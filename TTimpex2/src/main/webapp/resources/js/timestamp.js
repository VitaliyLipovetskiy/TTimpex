let checked= false;
// let table;
let reportAjaxUrl = "api/ts/";

const ctx = {
    ajaxUrl: reportAjaxUrl,
    updateTable: function () {
        $.get(reportAjaxUrl, updateTableByData);
    }
}

function getDT() {
    $.ajax({
        url: reportAjaxUrl,
        success: function (data) {
            // console.log(data);

            let countDays = data.columnTos.length;

            $('th#month').attr('colSpan', countDays).text('Август (25)');
            $('#datatable>thead').append('<tr id="days"></tr>');
            let days = $('#datatable>thead>tr#days');
            for (let i = 0; i < countDays; i++) {
                days.append('<th class="column-day"></th>');
            }

            columns = formColumns(data);
            // console.log(columns);
            ctx.datatableApi = $('#datatable').DataTable( {
                // dom: '<"top"f<"clear">>',
                data: data.data,
                columns: columns,
                order: [[1, "asc"]
                ],
                paging: false,
                deferRender: true,
                scrollX: true,
                scrollCollapse: true,
                fixedColumns: {
                    left: 2,
                    // right: 0
                },
                fixedHeader: {
                    header: true,
                    // footer: true
                },
                // initComplete: function () {
                //     var api = this.api();
                //     api.$('td').click(function () {
                //         // var data = this.row($(this).parents('tr')).data();
                //         console.log($(this).parent('td'));
                //         alert("tttttt");
                //         // api.search(this.innerHTML).draw();
                //     });
                // },
                // language: {
                //     fixedColumns: {
                //         button: 'Sticky Columns'
                //     }
                // },
                // buttons:[
                //     'fixedColumns'
                // ]
            } );
            // $(table.table().container()).on( 'click', 'td', function () {
            //     let cell = table.cell( this );
            //     console.log(cell.data());
            //     // $('#click-output').prepend(
            //     //     '<div>'+cell.data()+'</div>'
            //     // );
            // } );


        }
    });
}

// $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
//     let selected = data[2];
//
//     if (checked) {
//         console.log("1 " + checked + " " + selected);
//         if (selected) {
//             return true
//         }
//         else {
//             return false
//         }
//         // return selected;
//     } else {
//         console.log("2 " + checked + " " + selected);
//         return true;
//     }
// });

// function capitalizeFirstLetter(string) {
//     return string.charAt(0).toUpperCase() + string.slice(1);
// }

function formColumns(data) {
    // console.log(data);
    let columns = [
        {
            data: "id",
            visible: false,
            render: function (data, type, row) {
                if (type === "display") {
                    return "<div class='align-middle text-center'>" + data + "</div>";
                }
                return data;
            },
            orderable: false
        },// , "title": "#$", "width": 20},
        {
            data: "name",
            render: function (data, type, row) {
                if (type === "display") {
                    //
                    //  $('td', row).eq(5).addClass('highlight');
                    //
                    let result = "<div class='align-middle cell-name'>" + data + "</div><div class='text-right cell-font-red'>";
                    if (row.fine !== 0) {
                        result += row.fine;
                    } else {
                        result += "<br>";
                    }
                    if (row.worked !== 0) {
                        result += "</div><div class='text-right'>" + row.worked;
                    }
                    return result+ "</div>";
                }
                return data;
            },
            orderable: true
        },
        // {
        //   data: "name"
        // },
        {
            data: "choice",
            render: function (data, type, row) {
                if (type === "display") {
                    return "<div class='align-middle text-center cell-choice'><input type='checkbox' " + (data ? "checked" : "") + " onclick='choice($(this)," + row.id + ");'/></div>";//enable($(this)," + row.id + ");
                }
                return data;
            },
            orderable: false
        }
    ];
    // return columns;
    let columnTos = data.columnTos;
    for (let i = 0; i < columnTos.length; i++) {
        let day = columnTos[i];
        columns.push(
            {
                data: "dayTos." + i,
                title: '<div class="text-center">' + day.dayOfMonth + '<br>' + day.dayOfWeek + '</div>',
                // title: '<div class="container text-primary"><div class="row"><div class="col">' + i + '<br>sa</div></div></div>',
                orderable: false,
                defaultContent: "",
                render: function (data, type, row) {
                    if (type === "display") {
                        // Приход
                        let result = '<div id="container"><div class="text-right cell-time';
                        if (data.cameCorrectTime.length === 0) {
                            if (data.dayOff === true) {
                                result += ' box cell-blue';
                            } else if (data.cameAutoTime.length === 0) {
                                result += ' box cell-red';
                            }
                            result += '">' + data.cameAutoTime;
                        } else {
                            result += ' correct-time">' + data.cameCorrectTime;
                        }
                        result += '</div><div class="text-right cell-time';
                        // Уход
                        if (data.exitCorrectTime.length === 0) {
                            if (data.dayOff === true) {
                                result += ' box cell-blue';
                            } else if (data.exitAutoTime.length === 0) {
                                result += ' box cell-red';
                            }
                            result += '">' + data.exitAutoTime;
                        } else {
                            result += ' correct-time">' + data.exitCorrectTime;
                        }
                        result += '</div></div><div id="container-fine" class="text-center cell-font-red">';
                        // Штрафы
                        if (data.fine !== 0) {
                            result += data.fine;
                        }
                        // Отработанные часы
                        if (row.accountingForHoursWorked === true) {
                            result += '</div><div id="container-worked" class="text-center';
                            if (data.dayOff === true) {
                                result += '">' + (data.worked === 0 ? '' : data.worked);
                            } else {
                                if (data.worked === 0) {
                                    result += ' cell-font-red'
                                }
                                result += '">' + data.worked;
                            }
                        }
                        return result + '</div>';
                    }
                    return data;
                }
            });
    }
    return columns;
}

function choice(chkbox, id) {
    ctx.datatableApi.cell((id - 1), 2).data(chkbox.is(":checked"));

//  https://stackoverflow.com/a/22213543/548473
//     $.ajax({
//         url: userAjaxUrl + id,
//         type: "POST",
//         data: "enabled=" + enabled
//     }).done(function () {
//         chkbox.closest("tr").attr("data-user-enabled", enabled);
//         successNoty(enabled ? "common.enabled" : "common.disabled");
//     }).fail(function () {
//         $(chkbox).prop("checked", !enabled);
//     });
}

$(function() {

    getDT();

});