// let table;
const employeesAjaxUrl = "api/employees/";
const departmentAjaxUrl = "api/department/";
const departments = [];

const ctx = {
    ajaxUrl: employeesAjaxUrl,
    updateTable: function () {
        $.get(employeesAjaxUrl, updateTableByData);
    }
}

$(function () {
    $.ajax({
        url: employeesAjaxUrl,
        success: function (data) {
            let groupColumn = 2;
            ctx.datatableApi = $('#datatable').DataTable({
                data: data,
                columns: [
                    {
                        data: 'name',
                        orderable: false
                    },
                    {
                        data: 'cardId',
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data === null) return '';
                                return "<div class='align-middle text-center'>" + data + "</div>";
                            }
                            return data;
                        },
                        orderable: false
                    },
                    {
                        data: 'department',
                        visible: false,
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data === null) {
                                    return '';
                                } else {
                                    return data;
                                }
                            }
                            return data;
                        },
                        orderable: false
                    },
                    {
                        data: 'startTime',
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data === null) return '';
                                return "<div class='align-middle text-center'>" + data.substring(0,5) + "</div>";
                            }
                            return data;
                        },
                        orderable: false
                    },
                    {
                        data: 'endTime',
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (data === null) return '';
                                return "<div class='align-middle text-center'>" + data.substring(0,5) + "</div>";
                            }
                            return data;
                        },
                        orderable: false
                    },
                    {
                        orderable: false,
                        defaultContent: "",
                        render: function renderEditBtn(data, type, row) {
                            if (type === "display") {
                                return "<div class='align-middle text-center'><a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a></div>";
                            }
                        }
                    },
                    {
                        orderable: false,
                        defaultContent: "",
                        render: function renderDeleteBtn(data, type, row) {
                            if (type === "display") {
                                return "<div class='align-middle text-center'><a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a></div>";
                            }
                        }
                    },
                    {
                        data: 'worked',
                        visible: false,
                        // render: function (data, type, row) {
                        //     if (type === "display") {
                        //         return "<div class='align-middle text-center cell-choice'><input type='checkbox' " + (data ? "checked" : "") + " onclick=''/></div>";
                        //     }
                        //     return data;
                        // },
                        orderable: false
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
                createdRow: function (row, data, dataIndex) {
                    // console.log(data.worked);
                    if (!data.worked) {
                //         // console.log(data);
                        $(row).attr("data-employee-worked", true);
                    }
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
                            //     if (last !== group) {
                            //         $(rows)
                            //             .eq(i)
                            //             .before('<tr class="group"><td colspan="6">' + group.name + '</td></tr>');
                            //
                            //         last = group;
                            //     }
                            // } else {
                                if (last !== group) {
                                    $(rows)
                                        .eq(i)
                                        .before('<tr class="group"><td colspan="6">' + group + '</td></tr>');

                                    last = group;
                                }
                            // }
                        });
                }
            });

            ctx.datatableApi.column(7).search(true).draw();

            form = $('#detailsForm');

        }
    });

});

function save() {
    let data = form.serializeArray();
    let dataForm = {};
    dataForm.id = data.find(v => v.name === 'id').value;
    dataForm.firstName = data.find(v => v.name === 'firstName').value;
    dataForm.lastName = data.find(v => v.name === 'lastName').value;
    dataForm.middleName = data.find(v => v.name === 'middleName').value;
    dataForm.cardId = data.find(v => v.name === 'cardId').value;
    let department = $('#department option:selected');
    if (department.val() === '0' || department.val() === undefined) {
        dataForm.department = null;
    } else {
        dataForm.department = {id: department.val(), name: department.text()};
    }
    let worked = data.find(v => v.name === 'worked');
    if (worked === undefined) {
        dataForm.worked = false;
    } else {
        dataForm.worked = worked.value;
    }
    dataForm.startTime = data.find(v => v.name === 'startTime').value;
    dataForm.endTime = data.find(v => v.name === 'endTime').value;

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
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
    $.get(departmentAjaxUrl, function (data) {
        $.each(data, function (key, value) {
            if (departments.filter(v => v.id === value.id).length === 0) {
                departments.push(value);
                $('#department').append('<option value="'+value.id+'">'+value.name+'</option>')
            }
        });
    });
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html(i18n["editTitle"]);
    updateDepartments();
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            if (key === 'worked') {
                $("#worked").prop('checked', value);
            } else if (key === 'department') {
                if (data.department !== null) {
                    $('select[id=department]').val(data.department.id);
                }
            }
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function selectFilter(chkbox) {
    if (chkbox.is(":checked")) {
        ctx.datatableApi.column(7).search('').draw();
    } else {
        ctx.datatableApi.column(7).search(true).draw();
    }
}

function changeWorked() {
    form.find("input[name='worked']").val($("#worked").is(':checked'));
}