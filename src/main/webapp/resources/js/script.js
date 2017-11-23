$(document).ready(function () {
    $("table tr:gt(0) td").click(function () {
        if ($(this).children()[0] === undefined || $(this).children().length > 2) {
            $("table tr:gt(0)").removeClass("table-row-clicked");
            $(this).parent().addClass("table-row-clicked");
        }
    });
});
$("table tr td a").click(function () {
    if ($(this).attr("accountLink") !== undefined) {
        currentAccountLink = $(this).attr("accountLink");
        $("table tr:gt(0)").removeClass("table-row-clicked");
        $("tr[accountNumber=" + currentAccountLink + "]").addClass("table-row-clicked");
    }
});

