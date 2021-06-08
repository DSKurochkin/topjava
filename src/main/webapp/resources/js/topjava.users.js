const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        updateTableCommon();
    }
};

function sendEnable(id, isEnable) {
    $.ajax({
        type: "PATCH",
        dataType: "json",
        contentType: "application/json",
        data: isEnable.toString(),
        url: userAjaxUrl + id
    }).done(function () {
        successNoty(isEnable === true ? $("#mesEnable").attr("value") : $("#mesDisable").attr("value"));
        isEnable ? document.getElementById(id).style.opacity = 1 : document.getElementById(id).style.opacity = 0.3;

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
    $(".checkbox").click(function () {
        let box = $(this)
        if (confirm('Are you sure?')) {
            sendEnable(box.attr("id"), box.is(":checked"));
        }
    });

});