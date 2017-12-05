var saveButton = document.getElementById("schemaSaveButton");
var canvas = document.getElementById("graphicFrame");

saveButton.onclick = function () {
    graphicFrame.save();
    saveButton.style.display = "none";
};

document.getElementById("scaleUp").onclick = function () {
    graphicFrame.scaleUp();
};

document.getElementById("scaleDown").onclick = function () {
    graphicFrame.scaleDown();
};

document.getElementById("setting-icon").onclick = function () {
    let settings = document.getElementById("schema-settings");
    if (settings.style.display === "block") {
        settings.style.display = "none";
    } else {
        settings.style.display = "block";
    }
    
};
window.onresize = function (ev) {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
    graphicFrame.draw();
};


function GraphicFrame() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight - canvas.offsetTop;
    var context = canvas.getContext("2d");
    var bias = new Point(0, 0);
    var connector = new Connector();
    var chartModel = new ChartModel(new JsonParser(connector.getStringFromServer()));
    var eventListener = new GraphicFrameEventListener();
    var activeChartObject = null;
    var scale = 1;

    var observers = [];
    var oldPosition = null;
    var workWithObject = null;
    canvas.addEventListener("mousemove", function (event) {
        if (workWithObject === null) {
            let flag = false;
            if (activeChartObject !== null) {
                flag = true;
            }
            activeChartObject = chartModel.findChartObjectByPoint(new Point(event.pageX - canvas.offsetLeft + bias.x, event.pageY - canvas.offsetTop + bias.y), scale, TypeViewChartObject.PASSIVE);
            if (flag && activeChartObject === null) {
                for (let i = 0; i < observers.length; i++) {
                    observers[i].mouseExitedChartObject();
                }
            }
            if (!flag && activeChartObject !== null) {
                for (let i = 0; i < observers.length; i++) {
                    observers[i].hoverChartObject(activeChartObject);
                }
            }
        }
        if (oldPosition !== null) {
            var dX = (oldPosition.x - event.pageX) / scale;
            var dY = (oldPosition.y - event.pageY) / scale;
            if (workWithObject !== null) {
                chartModel.moveCharObject(workWithObject, new Point(-dX, -dY));
                for (let i = 0; i < observers.length; i++) {
                    observers[i].moveChartObject(workWithObject);
                }
            } else {
                bias.x += dX * scale;
                bias.y += dY * scale;
            }
            oldPosition = new Point(event.pageX, event.pageY);
        }
        graphicFrame.draw();
    });
    canvas.addEventListener("mousedown", function (event) {
        oldPosition = new Point(event.pageX, event.pageY);
        if (activeChartObject !== null) {
            workWithObject = activeChartObject;
        }
    });
    canvas.addEventListener("mouseup", function (event) {
        workWithObject = null;
        oldPosition = null;
    });

    this.addEventListener = function (eventListener) {
        observers.push(eventListener);
    };
    this.scaleUp = function () {
        scale += 0.1;
        this.draw();
    };
    this.scaleDown = function () {
        scale -= 0.1;
        this.draw();
    };
    this.draw = function () {
        context.fillStyle = "#222222";
        context.fillRect(0, 0, canvas.width, canvas.height);
        let lines = chartModel.getAllLines();
        for (let i = 0; i < lines.length; i++) {
            lines[i].draw(context, bias, scale);
        }

        let chartObjects = chartModel.getAllChartObjects();
        for (let i = 0; i < chartObjects.length; i++) {
            chartObjects[i].draw(context, bias, TypeViewChartObject.PASSIVE, scale);
        }

        if (activeChartObject !== null) {
            context.fillStyle = "rgba(0, 0, 0, 0.85)";
            context.fillRect(0, 0, canvas.width, canvas.height);
            let chartActiveLines = chartModel.getLinesByChartObject(activeChartObject);
            for (let i = 0; i < chartActiveLines.length; i++) {
                chartActiveLines[i].draw(context, bias, scale);
            }
            activeChartObject.draw(context, bias, TypeViewChartObject.ACTIVE, scale);
            let incidentObjects = chartModel.getIncidentObjects(activeChartObject);
            for (let i = 0; i < incidentObjects.length; i++) {
                incidentObjects[i].draw(context, bias, TypeViewChartObject.ACTIVE, scale);
            }
        }
        drawCenter();
    };
    this.save = function () {
        console.log(connector.sendChanges(chartModel.getChanged()));
        chartModel.cleanChahges();
    };
    let drawCenter = function () {
        context.strokeStyle = "#FF0000";
        context.beginPath();
        context.moveTo(-bias.x, -40 - bias.y);
        context.lineTo(-bias.x, 40 - bias.y);
        context.stroke();
        context.closePath();
        context.beginPath();
        context.moveTo(40 - bias.x, -bias.y);
        context.lineTo(-40 - bias.x, -bias.y);
        context.stroke();
        context.closePath();
    };

}
function GraphicFrameEventListener() {
    this.hoverChartObject = function (chartObject) {
        console.error("Method hoverChartObject must be overriten");
    };

    this.moveChartObject = function (chartObject) {
        console.error("Method moveChartObject must be overriten");
    };

    this.mouseExitedChartObject = function () {
        console.error("Method mouseExited must be overriten");
    };
}

function ChartModel(parser) {
    var changedObjects = [];
    var chartObjects = parser.getObjects();
    var matrixIncidence = parser.getMatrix();

    this.cleanChahges = function () {
        changedObjects = [];
    };
    this.getChanged = function () {
        var stringOutput = "";
        for (let i = 0; i < changedObjects.length; i++) {
            stringOutput += changedObjects[i].getId() + "_";
            stringOutput += changedObjects[i].getPosition().x + "_";
            stringOutput += changedObjects[i].getPosition().y;
            if (i < changedObjects.length - 1) {
                stringOutput += "#";
            }
        }
        return stringOutput;
    };

    this.getIndexByChartObject = function (chartObject) {
        for (let i = 0; i < chartObjects.length; i++) {
            if (chartObjects[i] === chartObject) {
                return i;
            }
        }
    };

    this.findChartObjectByPoint = function (point, scale, typeRendering) {
        for (let i = 0; i < chartObjects.length; i++) {
            if (chartObjects[i].containsPoint(point, scale, typeRendering) === true) {
                return chartObjects[i];
            }
        }

        return null;
    };
    this.moveCharObject = function (charObject, point) {
        if (charObject.type === "crr") {
            return;
        }
        if (charObject.type === "acc") {
            let index = this.getIndexByChartObject(charObject);
            let indexCorrespondence = [];
            for (let i = 0; i < matrixIncidence.length; i++) {
                if (matrixIncidence[index][i] === "t" || matrixIncidence[i][index] === "t") {
                    indexCorrespondence.push(i);
                }
            }

            for (let i = 0; i < indexCorrespondence.length; i++) {
                for (let j = 0; j < matrixIncidence.length; j++) {
                    if (matrixIncidence[indexCorrespondence[i]][j] === "t" && j !== index) {
                        chartObjects[indexCorrespondence[i]].position = new Point(charObject.position.x + (chartObjects[j].position.x - charObject.position.x) / 2
                                , charObject.position.y + (chartObjects[j].position.y - charObject.position.y) / 2);
                    }

                    if (matrixIncidence[j][indexCorrespondence[i]] === "t" && j !== index) {
                        chartObjects[indexCorrespondence[i]].position = new Point(charObject.position.x + (chartObjects[j].position.x - charObject.position.x) / 2
                                , charObject.position.y + (chartObjects[j].position.y - charObject.position.y) / 2);
                    }
                }
            }
        }
        charObject.move(point);

        for (let i = 0; i < changedObjects.length; i++) {
            if (charObject.id === changedObjects[i].id) {
                return;
            }
        }
        changedObjects.push(charObject);
    };

    this.getAllLines = function () {
        let lines = [];
        for (var i = 0; i < matrixIncidence.length; i++) {
            for (var j = 0; j < matrixIncidence.length; j++) {
                if (matrixIncidence[i][j] !== " ") {
                    lines.push(new Line(chartObjects[i].position, chartObjects[j].position, "#fff"));
                }
            }
        }
        return lines;
    };
    this.getAllChartObjects = function () {
        return chartObjects;
    };

    this.getLinesByDocument = function (document) {
        let index = this.getIndexByChartObject(document);
        let lines = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] === "d") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#fff"));
                if (chartObjects[i].type === 'crr') {
                    let correspondenceLines = this.getLinesByCorrespondence(chartObjects[i]);
                    for (let j = 0; j < correspondenceLines.length; j++) {
                        lines.push(correspondenceLines[j]);
                    }
                }
            }
        }

        return lines;
    };
    this.getLinesByRole = function (role) {
        let index = this.getIndexByChartObject(role);
        let lines = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] === "d") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#fff"));
            }
            if (matrixIncidence[index][i] === "s") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#ef04cb"));
            }
            if (matrixIncidence[i][index] === "s") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#a604f7"));
            }
        }
        return lines;
    };
    this.getLinesByAccount = function (account) {
        let index = this.getIndexByChartObject(account);
        let lines = [];
        let flag = -1;
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] === "t") {
                flag = i;
                let color = "#ff0000";
                if (matrixIncidence[i][index] === "t") {
                    color = "#f4ee42";
                }
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, color));
                for (let j = 0; j < matrixIncidence.length; j++) {
                    if (matrixIncidence[i][j] === "t" && j !== i) {
                        lines.push(new Line(chartObjects[i].position, chartObjects[j].position, color));
                    }
                }

            }
            if (flag !== i && matrixIncidence[i][index] === "t") {
                let color = "#00ff00";
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, color));
                for (let j = 0; j < matrixIncidence.length; j++) {
                    if (matrixIncidence[j][i] === "t" && j !== i) {
                        lines.push(new Line(chartObjects[i].position, chartObjects[j].position, color));
                    }
                }
            }

            if (matrixIncidence[index][i] === "s") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#ef04cb"));
            }
            if (matrixIncidence[i][index] === "s") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#a604f7"));
            }
        }
        return lines;
    };
    this.getLinesByCorrespondence = function (correspondence) {
        let index = this.getIndexByChartObject(correspondence);
        let lines = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] !== " " || matrixIncidence[i][index] !== " ") {
                lines.push(new Line(chartObjects[index].position, chartObjects[i].position, "#fff"));
            }
        }
        return lines;
    };
    this.getLinesByChartObject = function (chartObject) {
        if (chartObject.type === "doc") {
            return this.getLinesByDocument(chartObject);
        }
        if (chartObject.type === "role") {
            return this.getLinesByRole(chartObject);
        }
        if (chartObject.type === 'acc') {
            return this.getLinesByAccount(chartObject);
        }
        if (chartObject.type === 'crr') {
            return this.getLinesByCorrespondence(chartObject);
        }
        return [];
    };

    this.getIncidentObjectByDocument = function (document) {
        let index = this.getIndexByChartObject(document);
        let result = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] !== " ") {
                result.push(chartObjects[i]);
                if (chartObjects[i].type === "crr") {
                    for (let j = 0; j < matrixIncidence.length; j++) {
                        if (matrixIncidence[i][j] === "t" || matrixIncidence[j][i] === "t") {
                            result.push(chartObjects[j]);
                        }
                    }
                }
            }
        }
        return result;
    };

    this.getIncidentObjectByRole = function (role) {
        let index = this.getIndexByChartObject(role);
        let result = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] !== " " || matrixIncidence[i][index] !== " ") {
                result.push(chartObjects[i]);
            }
        }
        return result;
    };
    this.getIncidentObjectByAccount = function (acc) {
        let index = this.getIndexByChartObject(acc);
        let result = [];
        for (let i = 0; i < matrixIncidence.length; i++) {
            if (matrixIncidence[index][i] !== " " || matrixIncidence[i][index] !== " ") {
                result.push(chartObjects[i]);
                if (chartObjects[i].type === "crr") {
                    for (let j = 0; j < matrixIncidence.length; j++) {
                        if (matrixIncidence[i][j] === "t" || matrixIncidence[j][i] === "t") {
                            result.push(chartObjects[j]);
                        }
                    }
                }
            }
        }
        return result;
    };
    this.getIncidentObjects = function (chartObject) {
        if (chartObject.type === "doc") {
            return this.getIncidentObjectByDocument(chartObject);
        }
        if (chartObject.type === "role") {
            return this.getIncidentObjectByRole(chartObject);
        }
        if (chartObject.type === 'acc') {
            return this.getIncidentObjectByAccount(chartObject);
        }
        if (chartObject.type === 'crr') {
            return this.getIncidentObjectByRole(chartObject);
        }
        return [];
    };

}

//<editor-fold defaultstate="collapsed" desc="model classes">
function ChartObject(position, name, type, info, link, id) {
    this.position = position;
    this.id = id;
    this.name = name;
    this.type = type;
    this.info = info;
    this.link = link;
    this.rendererMap = new Map();
    this.rendererMap.set("default", new DefaultRenderer(this));
    this.containsPoint = function (point, scale, typeRendering) {
        if (this.rendererMap.get(typeRendering) === undefined) {
            typeRendering = "default";
        }
        return this.rendererMap.get(typeRendering).containsPoint(point, scale);
    };
    this.draw = function (context, bias, typeRendering, scale) {
        if (this.rendererMap.get(typeRendering) === undefined) {
            typeRendering = "default";
        }
        this.rendererMap.get(typeRendering).draw(context, bias, scale);
    };
    this.move = function (point) {
        this.position.x += point.x;
        this.position.y += point.y;
    };
    this.getId = function () {
        return this.id;
    };
    this.getPosition = function () {
        return this.position;
    };
}

function Role(position, name, type, info, link, id) {
    ChartObject.call(this, position, name, type, info, link, id);
    this.rendererMap.set(TypeViewChartObject.PASSIVE, new RolePassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new RoleActiveRenderer(this));
}

function Document(position, name, type, info, link, id) {
    ChartObject.call(this, position, name, type, info, link, id);
    this.rendererMap.set(TypeViewChartObject.PASSIVE, new DocumentPassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new DocumentActiveRenderer(this));
}

function Account(position, name, type, info, link, id) {
    ChartObject.call(this, position, name, type, info, link, id);
    this.rendererMap.set(TypeViewChartObject.PASSIVE, new AccountPassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new AccountActiveRenderer(this));
}

function Correspondence(position, name, type, info, link, id) {
    ChartObject.call(this, position, name, type, info, link, id);
    this.rendererMap.set(TypeViewChartObject.PASSIVE, new CorrespondencePassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new CorrespondenceActiveRenderer(this));
    this.move = function (point) {};
}

function Line(point1, point2, color) {
    this.draw = function (context, bias, scale) {
        context.beginPath();
        context.strokeStyle = color;
        context.moveTo((point1.x * scale - bias.x), (point1.y * scale - bias.y));
        context.lineTo((point2.x * scale - bias.x), (point2.y * scale - bias.y));
        context.stroke();
        context.closePath();
    };
}

function Connector() {
    this.url = "http://localhost:7995/Analitic/chart";
    this.getStringFromServer = function () {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', this.url, false);
        xhr.send();
        if (xhr.status !== 200) {
            console.error(xhr.status + ': ' + xhr.statusText);
            return null;
        } else {
            return xhr.responseText;
        }
    };
    this.sendChanges = function (changesString) {
        var xhr = new XMLHttpRequest();
        xhr.open('POST', this.url, false);
        xhr.send(changesString);
        if (xhr.status !== 200) {
            console.error(xhr.status + ': ' + xhr.statusText);
            return null;
        } else {
            return xhr.responseText;
        }
    };
}

function Parser(serverAnswer) {
    this.getObjects = function () {
        console.error("Method getObjects should be overritten");
    };
    this.getMatrix = function () {
        console.error("Method getMatrix should be overritten");
    };
}


function JsonParser(serverAnswer) {
    Parser.call(this, serverAnswer);
    this.getObjects = function () {
        let output = [];
        console.log(serverAnswer);
        let objects = JSON.parse(serverAnswer).objects;
        for (let i = 0; i < objects.length; i++) {
            if (objects[i].type === 'doc') {
                output.push(new Document(new Point(Number(objects[i].x), Number(objects[i].y)),
                        objects[i].name, objects[i].type, objects[i].info, objects[i].link, objects[i].id));
            }
            if (objects[i].type === 'role') {
                output.push(new Role(new Point(Number(objects[i].x), Number(objects[i].y)),
                        objects[i].name, objects[i].type, objects[i].info, objects[i].link, objects[i].id));
            }
            if (objects[i].type === 'crr') {
                output.push(new Correspondence(new Point(Number(objects[i].x), Number(objects[i].y)),
                        objects[i].name, objects[i].type, objects[i].info, objects[i].link, objects[i].id));
            }
            if (objects[i].type === 'acc') {
                output.push(new Account(new Point(Number(objects[i].x), Number(objects[i].y)),
                        objects[i].name, objects[i].type, objects[i].info, objects[i].link, objects[i].id));
            }

        }
        return output;
    };
    this.getMatrix = function () {
        return JSON.parse(serverAnswer).incedence;
    };
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="renderrer classes">
function Renderer(chartObject) {
    this.draw = function (context, bias, scale) {
        console.error("Method draw isn't overradden  in Render class");
    };
    this.containsPoint = function (point, scale) {
        console.error("Method containsPoint isn't overradden in Render class");
    };
}

function DefaultRenderer(chartObject) {
    Renderer.call(this, chartObject);
    this.width = 180;
    this.height = 40;
    this.fillColor = "#3e3e3e";
    this.color = "#ff000f";
    this.fontSize = 14;
    this.draw = function (context, bias, scale) {
        let biasCenter = new Point(bias.x + this.width / 2, bias.y + this.height / 2);
        context.fillStyle = this.fillColor;
        context.beginPath();
        context.fillRect(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y, this.width * scale, this.height * scale);
        context.strokeStyle = this.color;
        context.strokeRect(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y, this.width * scale, this.height * scale);
        context.closePath();
        context.fillStyle = this.color;
        context.font = (this.fontSize * scale) + "px Arial";
        var text = chartObject.name;
        context.fillText(text, chartObject.position.x * scale - biasCenter.x + (this.width * scale - context.measureText(text).width) / 2, chartObject.position.y * scale - biasCenter.y + this.height * scale / 2 + this.fontSize / 3);
        context.stroke();
    };
    this.containsPoint = function (point, scale) {
        let pointCenter = new Point(point.x + this.width / 2, point.y + this.height / 2);
        return pointCenter.x > chartObject.position.x * scale
                && pointCenter.x < chartObject.position.x * scale + this.width * scale
                && pointCenter.y > chartObject.position.y * scale
                && pointCenter.y < chartObject.position.y * scale + this.height * scale;
    };
}

function DocumentPassiveRenderer(chartObject) {
    DefaultRenderer.call(this, chartObject);
    this.color = "#ffffff";
}
function DocumentActiveRenderer(chartObject) {
    DocumentPassiveRenderer.call(this, chartObject);
    this.color = "#99ffb9";
    this.draw = function (context, bias, scale) {
        let temp = new DocumentPassiveRenderer(chartObject);
        temp.color = this.color;
        temp.draw(context, bias, scale);
        context.beginPath();
        context.fillStyle = "#ff3333";
        context.fillText(chartObject.info, 30, 30);
        context.stroke();
        context.closePath();
    };
}


function AccountPassiveRenderer(chartObject) {
    DefaultRenderer.call(this, chartObject);
    this.fillColor = "#3e3e3e";
    this.color = "#FFFFFF";
    var radius = 25;
    this.draw = function (context, bias, scale) {
        context.beginPath();
        context.arc(chartObject.position.x * scale - bias.x, chartObject.position.y * scale - bias.y, radius * scale, 0, 2 * Math.PI, false);
        context.fillStyle = this.fillColor;
        context.fill();
        context.strokeStyle = this.color;
        context.stroke();
        context.fillStyle = this.color;
        context.closePath();
        context.font = this.fontSize * scale + "px Arial";
        context.fillText(chartObject.name, chartObject.position.x * scale - bias.x + (radius * scale - context.measureText(chartObject.name).width) / 2 - radius * scale / 2, chartObject.position.y * scale - bias.y + this.fontSize / 3);
        context.stroke();
    };
    this.containsPoint = function (point, scale) {
        return (Math.pow(point.x - chartObject.position.x * scale, 2) + Math.pow(point.y - chartObject.position.y * scale, 2)) < Math.pow(radius * scale, 2);
    };
}
function AccountActiveRenderer(chartObject) {
    AccountPassiveRenderer.call(this, chartObject);
    this.color = "#ffb9d4";
}

function RolePassiveRenderer(chartObject) {
    DefaultRenderer.call(this, chartObject);
    this.color = "#FFFFFF";
    this.width = 30;
    this.height = 90;
    this.fillColor = "#222222";
    this.draw = function (context, bias, scale) {
        let biasCenter = new Point(bias.x + this.width / 2, bias.y + this.height / 2);
        context.strokeStyle = this.color;
        context.fillStyle = this.fillColor;
        context.fillRect(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y, this.width * scale, this.height * scale);
        context.beginPath();
        context.arc(chartObject.position.x * scale - biasCenter.x + this.width * scale / 2, chartObject.position.y * scale - biasCenter.y + this.width * scale / 2, this.width * scale / 2, 0, 2 * Math.PI);
        context.moveTo(chartObject.position.x * scale - biasCenter.x + this.width * scale / 2, chartObject.position.y * scale - biasCenter.y + this.height * scale / 3);
        context.lineTo(chartObject.position.x * scale - biasCenter.x + this.width * scale / 2, chartObject.position.y * scale - biasCenter.y + (7 * this.height * scale) / 9);
        context.lineTo(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y + this.height * scale);
        context.moveTo(chartObject.position.x * scale - biasCenter.x + this.width * scale / 2, chartObject.position.y * scale - biasCenter.y + (7 * this.height * scale) / 9);
        context.lineTo(chartObject.position.x * scale - biasCenter.x + this.width * scale, chartObject.position.y * scale - biasCenter.y + this.height * scale);
        context.moveTo(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y + (4 * this.height * scale) / 9);
        context.lineTo(chartObject.position.x * scale - biasCenter.x + this.width * scale, chartObject.position.y * scale - biasCenter.y + (4 * this.height * scale) / 9);
        context.closePath();
        context.stroke();
        context.fillStyle = this.color;
        context.font = (this.fontSize * scale) + "px Arial";
        context.fillText(chartObject.name, chartObject.position.x * scale - biasCenter.x + (this.width * scale - context.measureText(chartObject.name).width) / 2, chartObject.position.y * scale + this.height * scale - biasCenter.y + this.fontSize * scale);
        context.stroke();
    };
}
function RoleActiveRenderer(chartObject) {
    RolePassiveRenderer.call(this, chartObject);
    this.fillColor = "#000000";
    this.color = "#12ff4e";
}

function CorrespondencePassiveRenderer(chartObject) {
    DefaultRenderer.call(this, chartObject);
    this.fillColor = "#3e3e3e";
    this.color = "#FFFFFF";
    this.width = 30;
    this.height = 20;
    this.draw = function (context, bias, scale) {
        let biasCenter = new Point(bias.x + this.width / 2, bias.y + this.height / 2);
        context.beginPath();
        context.moveTo(chartObject.position.x * scale - biasCenter.x + this.width * scale / 2, chartObject.position.y * scale - biasCenter.y);
        context.lineTo(chartObject.position.x * scale - biasCenter.x + this.width * scale, chartObject.position.y * scale - biasCenter.y + this.height * scale);
        context.lineTo(chartObject.position.x * scale - biasCenter.x, chartObject.position.y * scale - biasCenter.y + this.height * scale);
        context.closePath();
        context.strokeStyle = this.color;
        context.stroke();
        context.fillStyle = this.fillColor;
        context.fill();
    };
}
function CorrespondenceActiveRenderer(chartObject) {
    CorrespondencePassiveRenderer.call(this, chartObject);
    this.fillColor = "#cc6428";
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="utilit classes">
function Point(x, y) {
    this.x = x;
    this.y = y;
}

var TypeViewChartObject = {
    PASSIVE: 0,
    ACTIVE: 1
};
//</editor-fold>


var graphicFrame = new GraphicFrame();
graphicFrame.draw();


graphicFrame.addEventListener(new function () {
    GraphicFrameEventListener.call(this);
    var infoMessageBlock = document.getElementById("info-chart-schema");

    this.moveChartObject = function (chartObject) {
        saveButton.style.display = "block";
    };

    this.hoverChartObject = function (chartObject) {
        infoMessageBlock.innerHTML = chartObject.info;
        infoMessageBlock.style.display = "block";
    };


    this.mouseExitedChartObject = function () {
        infoMessageBlock.style.display = "none";
    };
});