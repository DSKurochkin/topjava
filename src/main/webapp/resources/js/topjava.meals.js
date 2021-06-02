const mealAjaxUrl = "ui/meals/";
let isFiltered;
const ctx = {
    ajaxUrl: mealAjaxUrl
};

function on_filter() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: mealAjaxUrl + "filter?"
            + "startDate=" + document.getElementById("startDate").value
            + "&startTime=" + document.getElementById("startTime").value
            + "&endDate=" + document.getElementById("endDate").value
            + "&endTime=" + document.getElementById("endTime").value
    }).done(function (data) {
        isFiltered = true;
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function off_filter() {
    isFiltered = false;
    updateTable();
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});