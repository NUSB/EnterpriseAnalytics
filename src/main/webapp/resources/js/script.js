$(document).ready(function () {
    $("table tr:gt(0) td").click(function () {
        if ($(this).children()[0] === undefined || $(this).children().length > 2) {
            $("table tr:gt(0)").removeClass("table-row-clicked");
            $(this).parent().addClass("table-row-clicked");
        }
    });


    $("table tr td a").click(function () {
        if ($(this).attr("accountLink") !== undefined) {
            currentAccountLink = $(this).attr("accountLink");
            $("table tr:gt(0)").removeClass("table-row-clicked");
            $("tr[accountNumber=" + currentAccountLink + "]").addClass("table-row-clicked");
        }
    });

    $(".message-exit").click(function () {
        $(this).parent().parent().css("display", "none");
    });

//    Set standart action path to button in order to reset after clicking on the button
    var standartRolesFormActionPath = $("#form_roles").attr("action");
    function rulesButtonsToggle() {
        $("#roleAddButton").toggleClass("invisible");
        $("#roleEditButton").toggleClass("invisible");
        $("#roleCancelButton").toggleClass("invisible");
    }




//    Working with docs
    var clickedCheck = 0;
    $('a[pathUpdateCurrentRole]').click(function () {
        if (!clickedCheck) {
            var path = $(this).attr("pathUpdateCurrentRole");
            $("#form_roles").attr("action", path);
            rulesButtonsToggle();
            document_role = $(this).parent().parent().find(".document-role").text();
            document_description = $(this).parent().parent().find(".document-description").text();
            $("#description_role").val(document_description);
            $("#document-role").toggleClass("invisible");
            $("#editDocumentRole").toggleClass("invisible");
            $("#editDocumentRole").attr("value", document_role);
            clickedCheck = 1;
        }
    });

    $("#roleCancelButton").click(function () {
        $('#form_roles').attr("action", standartRolesFormActionPath);
        $("#description_role").val("");
        $("#document-role").attr("value", "");
        $("#document-role").toggleClass("invisible");
        $("#editDocumentRole").toggleClass("invisible");
        rulesButtonsToggle();
        clickedCheck = 0;
    });
});
