function GraphicFrame() {
    var canvas = document.getElementById("graphicFrame");
    canvas.width = window.innerWidth - 5;
    canvas.height = window.innerHeight - canvas.offsetTop - 5;
    var context = canvas.getContext("2d");
    var bias = new Point(0, 0);
    var connector = new Connector();
    var chartModel = new ChartModel(new JsonParser(connector.getStringFromServer()));
    var activeChartObject = null;
    var scale = 1;

    var oldPosition = null;
    var workWithObject = null;
    canvas.addEventListener("mousemove", function (event) {
        if (workWithObject === null) {
            activeChartObject = chartModel.findChartObjectByPoint(new Point(event.pageX - canvas.offsetLeft + bias.x, event.pageY - canvas.offsetTop + bias.y), scale, TypeViewChartObject.PASSIVE);
        }
        if (oldPosition !== null) {
            var dX = (oldPosition.x - event.pageX) / scale;
            var dY = (oldPosition.y - event.pageY) / scale;

            if (workWithObject !== null) {
                chartModel.moveCharObject(workWithObject, new Point(-dX, -dY));
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

            let activeChartObjects = chartModel.getLinesByChartObject(activeChartObject);

            for (let i = 0; i < activeChartObjects.length; i++) {
                activeChartObjects[i].draw(context, bias, scale);
            }
            activeChartObject.draw(context, bias, TypeViewChartObject.ACTIVE, scale);

            //todo: Код по отображению смежных елеметов диаграммы 
        }
        drawCenter();
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

function ChartModel(parser) {
    var chartObjects = parser.getObjects();
//        chartObjects[0] = new Role(new Point(200, 100), "Дерик", "role", "role_1", "role/1");
//    [
//        new Document(new Point(400, 100), "Поступление на счет", "doc", "doc_1", "doc/1")
//                , new Document(new Point(350, 200), "Приходная накладная", "doc", "doc_2", "doc/2")
//                , new Role(new Point(200, 100), "Кассир", "role", "role_1", "role/1")
//                , new Role(new Point(50, 200), "Кладовщик", "role", "role_2", "role/2")
//                , new Account(new Point(500, 500), "30", "acc", "Касса", "account/1")
//                , new Account(new Point(500, 700), "40", "acc", "Рассчеты с поставщиками", "account/2")
//                , new Correspondence(new Point(480, 600), "", "crr", "Дт 40:Кт 30", "correspondence/2")
//                , new Account(new Point(800, 800), "50", "acc", "Касса2", "account/2")
//                , new Account(new Point(900, 900), "60", "acc", "Рассчеты с поставщиками2", "account/3")
//                , new Correspondence(new Point(680, 800), "", "crr", "Дт 50:Кт 60", "correspondence/2")
//    ];
    console.log(chartObjects);
    var matrixIncidence =
            parser.getMatrix();

//            [
//        [" ", " ", "d", " ", " ", " ", " ", " ", " ", " "],
//        [" ", " ", " ", "d", " ", " ", " ", " ", " ", "d"],
//        ["d", " ", " ", " ", " ", " ", " ", " ", " ", " "],
//        [" ", "d", "s", " ", " ", " ", " ", " ", " ", " "],
//        [" ", " ", " ", " ", " ", " ", "t", " ", " ", " "],
//        [" ", " ", " ", " ", " ", " ", "t", " ", " ", " "],
//        [" ", " ", " ", " ", "t", "t", " ", " ", " ", " "],
//        [" ", " ", " ", " ", " ", " ", " ", " ", " ", "t"],
//        [" ", " ", " ", " ", " ", " ", " ", " ", " ", " "],
//        [" ", "d", " ", " ", "t", " ", " ", " ", " ", " "]
//    ];
    console.log(matrixIncidence);
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

    this.getIndexByChartObject = function (chartObject) {
        for (let i = 0; i < chartObjects.length; i++) {
            if (chartObjects[i] === chartObject) {
                return i;
            }
        }
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

    this.getRelatedObjects = function () {
        console.log("asdasda");
    };

    this.getAllChartObjects = function () {
        return chartObjects;
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
    };

}

//<editor-fold defaultstate="collapsed" desc="model classes">
function ChartObject(position, name, type, info, link) {
    this.position = position;
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
}

function Role(position, name, type, info, link) {
    ChartObject.call(this, position, name, type, info, link);

    this.rendererMap.set(TypeViewChartObject.PASSIVE, new RolePassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new RoleActiveRenderer(this));
}

function Document(position, name, type, info, link) {
    ChartObject.call(this, position, name, type, info, link);
    this.rendererMap.set(TypeViewChartObject.PASSIVE, new DocumentPassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new DocumentActiveRenderer(this));

}

function Account(position, name, type, info, link) {
    ChartObject.call(this, position, name, type, info, link);

    this.rendererMap.set(TypeViewChartObject.PASSIVE, new AccountPassiveRenderer(this));
    this.rendererMap.set(TypeViewChartObject.ACTIVE, new AccountActiveRenderer(this));
}

function Correspondence(position, name, type, info, link) {
    ChartObject.call(this, position, name, type, info, link);

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
    this.url = "http://localhost:7995/Analitic/";
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
        let objects = JSON.parse(serverAnswer).objects;
        for (let i = 0; i < objects.length; i++) {
                if (objects[i].type === 'doc') {
                    output.push(new Document(new Point(Number(objects[i].x), Number(objects[i].y)),
                            objects[i].name, objects[i].type, objects[i].info, objects[i].link));
                }
                if (objects[i].type === 'role') {
                    output.push(new Role(new Point(Number(objects[i].x), Number(objects[i].y)),
                            objects[i].name, objects[i].type, objects[i].info, objects[i].link));
                }
                if (objects[i].type === 'crr') {
                    output.push(new Correspondence(new Point(Number(objects[i].x), Number(objects[i].y)),
                            objects[i].name, objects[i].type, objects[i].info, objects[i].link));
                }
                if (objects[i].type === 'acc') {
                    output.push(new Account(new Point(Number(objects[i].x), Number(objects[i].y)),
                            objects[i].name, objects[i].type, objects[i].info, objects[i].link));
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
