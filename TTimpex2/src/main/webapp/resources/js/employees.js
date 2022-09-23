const employeesAjaxUrl = "api/employees/";
const departmentAjaxUrl = "api/department/";
const workedAjaxUrl = "api/worked/";
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
                        data: 'departmentName',
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
                        data: 'recruitment',
                        defaultContent: "",
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (typeof data === "string") {
                                    // let parts = data.split('-');
                                    // let date = new Date(parts[0], parts[1] - 1, parts[2]);
                                    // console.log(date.toLocaleDateString('ru-RU'));
                                    return "<div class='align-middle text-center cell-choice'>" + data.split('-').reverse().join('.') + "</div>";
                                }
                                return "<div class='align-middle text-center cell-choice'></div>";
                            }
                            return data;
                        }
                    },
                    {
                        data: 'dismissal',
                        render: function (data, type, row) {
                            if (type === "display") {
                                if (typeof data === "string") {
                                    return "<div class='align-middle text-center cell-choice'>" + data.split('-').reverse().join('.') + "</div>";
                                }
                                return "<div class='align-middle text-center cell-choice'></div>";
                            }
                            return data;
                        },
                        orderable: false,
                        defaultContent: "",
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
                        // data: false,
                        visible: false,
                        render: function (data, type, row) {
                            if (row.recruitment === null && row.dismissal === null) {
                                return false;
                            } else if (row.dismissal === null) {
                                return true;
                            }
                            return false;
                        },
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
                fixedHeader: {
                    header: true,
                    // footer: true
                },
                createdRow: function (row, data, dataIndex) {
                    let worked = true;
                    if (data.recruitment === null && data.dismissal === null) {
                        worked = true;
                    } else {
                        if (data.dismissal === null) {
                            worked = false;
                        }
                    }
                    // console.log(row);
                    // console.log(data);
                    // console.log(dataIndex);
                    // console.log(data.name + ' ' + data.recruitment + ' ' + data.dismissal + ' ' + worked);
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
                                        .before('<tr class="group"><td colspan="7">' + group + '</td></tr>');

                                    last = group;
                                }
                            // }
                        });
                }
            });

            ctx.datatableApi.column(8).search(true).draw();

            form = $('#detailsForm');

        }
    });

});

function save() {
    let data = form.serializeArray();
    // console.log(data);
    let dataForm = {};
    dataForm.id = data.find(v => v.name === 'id').value;
    dataForm.firstName = data.find(v => v.name === 'firstName').value;
    dataForm.lastName = data.find(v => v.name === 'lastName').value;
    dataForm.middleName = data.find(v => v.name === 'middleName').value;
    dataForm.cardId = data.find(v => v.name === 'cardId').value;
    dataForm.name = '';
    let department = $('#department option:selected');
    if (department.val() === '0' || department.val() === undefined) {
        dataForm.department = null;
    } else {
        dataForm.department = {id: department.val(), name: department.text()};
    }
    dataForm.startTime = data.find(v => v.name === 'startTime').value;
    dataForm.endTime = data.find(v => v.name === 'endTime').value;
    dataForm.recruitment = data.find(v => v.name === 'recruitment').value;
    dataForm.dismissal = data.find(v => v.name === 'dismissal').value;

    console.log(dataForm);

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
        $.each(data, function (key, value) {
            if (key === 'department') {
                if (data.department !== null) {
                    $('select[id=department]').val(data.department.id);
                }
            }
            form.find("input[name='" + key + "']").val(value);
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
        ctx.datatableApi.column(8).search('').draw();
    } else {
        ctx.datatableApi.column(8).search(true).draw();
    }
}
