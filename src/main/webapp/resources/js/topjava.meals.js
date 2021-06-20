const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: "profile/meals/filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$("#startDate").datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
})

$("#endDate").datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
})

$("#startTime").datetimepicker({
    datepicker: false,
    format: 'H:i'
})

$("#endTime").datetimepicker({
    datepicker: false,
    format: 'H:i'
})

$("#dateTime").datetimepicker({
    format: 'Y-m-d H:i'
})
$.ajaxSetup({
    converters: {
        "text json": function (text) {
            let json = JSON.parse(text);
            $(json).each(function () {
                if (this.hasOwnProperty("dateTime")) {
                    this.dateTime = this.dateTime.substr(0, 16).replace("T", " ");
                }
            });
            return json;
        }
    }
});

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        return date;
                    }
                },
                {
                    "data": "description",
                    "render": function (data, type, row) {
                        return data;
                    }
                },
                {
                    "data": "calories",
                    "render": function (data, type, row) {
                        return data;
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }

            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            },
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});