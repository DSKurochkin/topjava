const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

function sendEnable(id, isEnable) {
    $.ajax({
        type: "PATCH",
        dataType: "json",
        contentType: "application/json",
        data: isEnable.toString(),
        url: "rest/" + userAjaxUrl + id
    }).done(function () {
        // let mes = isEnable === true ? "Enable" : "Disable"
        let mes = isEnable === true ? $("#mesEnable").attr("value") : $("#mesDisable").attr("value")
        successNoty(mes)
    })
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});