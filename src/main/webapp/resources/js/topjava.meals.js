const mealAjaxUrl = "ui/meals/";
let isFiltered;
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        if (isFiltered) {
            on_filter();
        } else {
            updateTableCommon();
        }
    }
};

function on_filter() {
    $.ajax({
        type: "GET",
        dataType: "json",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
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