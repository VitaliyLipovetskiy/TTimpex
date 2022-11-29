const ajaxUrl = "api/scodes/";

const ctx = {
    ajaxUrl: ajaxUrl,
    updateTable: function () {
        $.get(ajaxUrl + "employees/", updateTableByData);
    }
}

function addRow() {
    $('#card').prop('readonly', false);
    $('#method').val('POST');
}

function clearEmployee() {
    $('#employee').val("");
}

$(function () {
    $.ajax({
        url: ajaxUrl + "employees/",
        success: function (data) {
            ctx.datatableApi = $('#datatable').DataTable({
                data: data,
                columns: [
                    {
                        data: 'id',
                        orderable: true
                    },
                    {
                        data: 'scode',
                        orderable: false
                    },
                    {
                        data: 'employeeNames',
                        orderable: true
                    }
                ],
                paging: false
            });
            form = $('#detailsForm');
        }
    });

    $('#datatable').on('dblclick', 'td', function () {
        $("#modalTitle").html(i18n["editTitle"]);

        let rowData = ctx.datatableApi.row(this).data();

        $('#method').val('PATCH');
        $('#card').val(rowData.id).prop('readonly', true);
        $('#scode').val(rowData.scode);
        $('#employee').val(rowData.employeeNames);
        $('#editRow').modal();
    })
});

function save() {
    let dataForm = {};
    dataForm.id = $('#card').val();
    dataForm.scode = $('#scode').val();
    let id = '';
    let method = $('#method').val();
    if (method === 'PATCH') {
        id = dataForm.id;
    }
    $.ajax({
        type: method,
        url: ctx.ajaxUrl + id,
        data: JSON.stringify(dataForm),
        dataType: "json",
        contentType: "application/json"
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        // successNoty("common.saved");
    });
    $.ajax({
        type: 'PATCH',
        url: ctx.ajaxUrl + 'clear/' + id
    // })
    //     .done(function () {
    //     $("#editRow").modal("hide");
        // ctx.updateTable();
        // successNoty("common.saved");
    });
}

