const employeesAjaxUrl = "api/employees/";
const departmentAjaxUrl = "api/departments/";
// const workedAjaxUrl = "api/worked/";
const departments = [];

const ctx = {
    ajaxUrl: employeesAjaxUrl,
    updateTable: function () {
        $.get(employeesAjaxUrl, updateTableByData);
    }
}

$(function () {
    $.ajax({
        url: ctx.ajaxUrl,
        success: function (data) {
            let groupColumn = 2;
            ctx.datatableApi = $('#datatable').DataTable({
                data: data,
                columns: [
                    {
                        data: 'fullName',
                        orderable: false
                    },
                    {
                        data: 'cards',
                        orderable: false,
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data == null) return '';
                                for (let i = 0; i < data.length; i++) {

                                }
                                return "<div class='align-middle text-center'>" + data.map(card => card.id).join(', ') + "</div>";
                            }
                            return data;
                        }
                    },
                    {
                        data: 'departmentName',
                        orderable: false,
                        visible: false,
                        render: function (data, type, row) {
                            if (type === "display") {
                                return data == null ? '' : data;
                            }
                            return data;
                        }
                    },
                    {
                        data: 'startTime',
                        orderable: false,
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data == null) return '';
                                return "<div class='align-middle text-center'>" + data.substring(0,5) + "</div>";
                            }
                            return data;
                        }
                    },
                    {
                        data: 'endTime',
                        orderable: false,
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data == null) return '';
                                return "<div class='align-middle text-center'>" + data.substring(0,5) + "</div>";
                            }
                            return data;
                        }
                    },
                    {
                        data: 'accountingForHoursWorked',
                        orderable: false,
                        defaultContent: "",
                        visible: true,
                        render: function (data, type, row) {
                            if (type === "display") {
                                return "<div class='align-middle text-center'><input type='checkbox' " + (data ? "checked" : "") + " onclick='return false;'/></div>";
                            }
                        }
                    },
                    {
                        data: 'recruitment',
                        orderable: false,
                        defaultContent: "",
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (typeof data === "string") {
                                    return "<div class='align-middle text-center cell-choice'>" + data.split('-').reverse().join('.') + "</div>";
                                }
                                return "<div class='align-middle text-center cell-choice'></div>";
                            }
                            return data;
                        }
                    },
                    {
                        data: 'dismissal',
                        orderable: false,
                        defaultContent: "",
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (typeof data === "string") {
                                    return "<div class='align-middle text-center cell-choice'>" + data.split('-').reverse().join('.') + "</div>";
                                }
                                return "<div class='align-middle text-center cell-choice'></div>";
                            }
                            return data;
                        },
                    },
                    {
                        orderable: false,
                        defaultContent: "",
                        render: function (data, type, row) {
                            if (type === "display") {
                                return "<div class='align-middle text-center'><a onclick='updateRow(\"" + row.id + "\");'><span class='fa fa-pencil'></span></a></div>";
                            }
                            return data;
                        }
                    },
                    {
                        // data: false,
                        orderable: false,
                        visible: false,
                        render: function (data, type, row) {
                            if (row.recruitment == null && row.dismissal == null) {
                                return false;
                            } else if (row.dismissal == null) {
                                return true;
                            }
                            return false;
                        }
                    }
                ],
                order: [[groupColumn,'asc']],
                paging: false,
                // deferRender: true,
                // scrollX: true,
                // scrollCollapse: true,
                // fixedColumns: {
                //     left: 3,
                    // righ
                // },
                fixedHeader: {
                    header: true,
                    // footer: true
                },
                createdRow: function (row, data, dataIndex) {
                    let worked = true;
                    if (data.recruitment == null && data.dismissal == null) {
                        worked = true;
                    } else {
                        if (data.dismissal == null) {
                            worked = false;
                        }
                    }
                    $(row).attr("data-employee-worked", worked);
                },
                // columnDefs: [
                //     {
                //         visible: false,
                //         targets: groupColumn
                //     },
                //     {
                //         visible: false,
                //         targets: 7
                //
                // }],
                drawCallback: function (settings) {
                    let api = this.api();
                    let rows = api.rows({ page: 'current' }).nodes();
                    let last = null;

                    api
                        .column(groupColumn, { page: 'current' })
                        .data()
                        .each(function (group, i) {
                            // if (group === null) {
                            //     if (last !== null) {
                            //         $(rows)
                            //             .eq(i)
                            //             .before('<tr class="group"><td colspan="7"></td></tr>');
                                    //
                                    // last = null;
                                // }
                            // } else {
                                if (last !== group && group !== '') {
                                    $(rows)
                                        .eq(i)
                                        .before('<tr class="group"><td colspan="8">' + group + '</td></tr>');

                                    last = group;
                                }
                            // }
                        });
                }
            });

            ctx.datatableApi.column(9).search(true).draw();

            form = $('#detailsForm');

        }
    });

});

function save() {
    let data = form.serializeArray();
    // console.log(form);
    // console.log(data);
    let dataForm = {};
    let id = data.find(v => v.name === 'id').value;
    let type = "PATCH";
    if (id === '') {
        type = "POST";
    }
    dataForm.firstName = data.find(v => v.name === 'firstName').value;
    dataForm.lastName = data.find(v => v.name === 'lastName').value;
    dataForm.middleName = data.find(v => v.name === 'middleName').value;
    // dataForm.cardId = data.find(v => v.name === 'cardId').value;
    let department = $('#department option:selected');
    if (department.val() !== '0' && department.val() !== undefined) {
        // dataForm.departmentId = null;
    // } else {
        dataForm.departmentId = department.val();
    }
    dataForm.startTime = data.find(v => v.name === 'startTime').value;
    dataForm.endTime = data.find(v => v.name === 'endTime').value;
    dataForm.recruitment = data.find(v => v.name === 'recruitment').value;
    dataForm.dismissal = data.find(v => v.name === 'dismissal').value;
    let timeTracking = data.find(v => v.name === 'accountingForHoursWorked');
    if (timeTracking === undefined) {
        timeTracking = false;
    } else {
        timeTracking = data.find(v => v.name === 'accountingForHoursWorked').value;
    }
    dataForm.accountingForHoursWorked = timeTracking;

    // console.log(dataForm);
    // console.log(type);
    // console.log(ctx.ajaxUrl + id);
    $.ajax({
        type: type,
        url: ctx.ajaxUrl + id,
        data: JSON.stringify(dataForm),
        dataType: "json",
        contentType: "application/json"
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        // successNoty("common.saved");
    });
}

function updateDepartments() {
    $.get(departmentAjaxUrl,
        function (data) {
            $.each(data, function (key, value) {
                if (departments.filter(v => v.id === value.id).length === 0) {
                    departments.push(value);
                    $('#department').append('<option value="' + value.id + '">' + value.name + '</option>')
                }
            });
        });
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html(i18n["editTitle"]);
    updateDepartments();
    $.get(ctx.ajaxUrl + id, function (data) {
        // console.log(data);
        $.each(data, function (key, value) {
            if (key === 'department') {
                if (data.department !== null) {
                    $('select[id=department]').val(data.department.id);
                }
            }
            form.find("input[name='" + key + "']").val(value);
            if (key === 'accountingForHoursWorked') {
                $("#accountingForHoursWorked").prop("checked", value);
            }
            if (key === 'recruitment') {
                $("#recruitment").prop('readonly', value !== null);
            }
            if (key === 'dismissal') {
                $("#dismissal").prop('readonly', value !== null);
            }
            // console.log(key + "=>" + form.find("input[name='" + key + "']").val());
        });
        // $.get(workedAjaxUrl + id, function (data) {
        //     $.each(data, function (key, value) {
        //         form.find("input[name='" + key + "']").val(value);
        //     })
        // });
        $('#editRow').modal();
    });
}

function selectFilter(chkbox) {
    if (chkbox.is(":checked")) {
        ctx.datatableApi.column(9).search('').draw();
    } else {
        ctx.datatableApi.column(9).search(true).draw();
    }
}

function changeAccountingForHoursWorked() {
    form.find("input[name='accountingForHoursWorked']").val($("#accountingForHoursWorked").is(':checked'));
}
