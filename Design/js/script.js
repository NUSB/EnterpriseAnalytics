$(document).ready(function () {
    // console.log($("table tr td").children()[0].tagName !== "A");

        $("table tr:gt(0) td").click(function () {
            if ($(this).children()[0] === undefined ||  $(this).children().length >= 2) {
                $("table tr:gt(0)").removeClass("table-row-clicked");
                $(this).parent().addClass("table-row-clicked");
            }
        });

    });

    $("table tr td a").click(function () {
        cureentAccountLink = $(this).attr("accountLink");
        $("table tr:gt(0)").removeClass("table-row-clicked");
        $("tr[accountNumber=1]").addClass("table-row-clicked");
        console.log("test");
    });
