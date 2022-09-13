let form;

function selectFilter(chkbox) {
    if (chkbox.is(":checked")) {
        ctx.datatableApi.column(2).search(true).draw();
    } else {
        ctx.datatableApi.column(2).search('').draw();
    }
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    updateDepartments();
    $("#editRow").modal();
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
        //     successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    // console.log("updateTableByData");
    // console.log(data);
    ctx.datatableApi.clear().rows.add(data).draw();
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}