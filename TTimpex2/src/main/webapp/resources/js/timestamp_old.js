const tsAjaxUrl = "profile/ts/";

const ctx = {
    ajaxUrl: tsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: tsAjaxUrl
        }).done(updateTableByData)
            // .fail(function (jqXHR, textStatus) {
            //     console.log(textStatus);
            // });
    }
}

// $(function() {
//     $(".main-table").clone(true).appendTo('#table-scroll').addClass('clone');
// });

$(function () {

    // $(".main-table").clone(true).appendTo('#table-scroll').addClass('clone');

    // console.log("timestamp makeEditable");
    let columns = [
        { data: "id", orderable: false},// , "title": "#$", "width": 20},
        { data: "name", render: $.fn.dataTable.render.text()}
    ];
    for (let i = 0; i < 31; i++) {
        columns.push(
            {
                data: "daysTo."+i,
                // title: '<div class="text-center">' + i + '<br>sa</div>',
                // title: function (data, type, row) {
                //     return '<div class="text-center">' + i + '<br>sa</div>';
                // },
                // title: '<div class="container text-primary"><div class="row"><div class="col">' + i + '<br>sa</div></div></div>',
                orderable: false ,
                defaultContent: "",
                render: function (data, type, row) {
                    if (type === "display") {
                        if (data.cameCorrectTime.length === 0 && data.cameAutoTime.length === 0
                        && data.exitCorrectTime.length ===0 && data.exitAutoTime.length === 0) {
                            return '';
                        }
                        let result = '<div id="container"><div class="text-right cell-time';
                        if (data.cameCorrectTime.length === 0) {
                            if (data.cameAutoTime.length === 0) {
                                result += ' box cell-red';
                            }
                            result += '">' + data.cameAutoTime;
                        } else {
                            result += ' correct-time">' + data.cameCorrectTime;
                        }
                        result += '</div><div class="text-right cell-time';
                        if (data.exitCorrectTime.length === 0) {
                            if (data.exitAutoTime.length === 0) {
                                result += ' box cell-red';
                            }
                            result += '">' + data.exitAutoTime;
                        } else {
                            result += ' correct-time">' + data.exitCorrectTime;
                        }
                        return result + '</div></div>';
                    }
                    return data;
                }
            });
    }

    ctx.datatableApi = $("#datatable").DataTable(
        {
            ajax: {
                url: ctx.ajaxUrl,
                dataSrc: "data"
            },
            columns: columns,
            order: [
                [
                    0,
                    "asc"
                ]
            ],
            paging: false,
            deferRender: true,
            scrollX: true,
            scrollCollapse: true,
            fixedColumns: {
                left: 2,
                // right: 0
            },
            // language: {
            //     fixedColumns: {
            //         button: 'Sticky Columns'
            //     }
            // },
            // buttons:[
            //     'fixedColumns'
            // ]
        }

    );

    // makeEditable({
    //     ajax: {
    //         url: ctx.ajaxUrl,
    //         dataSrc: "data"
    //     },
    //     columns: columns,
    //     order: [
    //         [
    //             0,
    //             "asc"
    //         ]
    //     ],
    //     paging: true,
    //     scrollX: true,
    //     scrollCollapse: true,
    //     fixedColumns: {
    //         left: 2,
    //         // right: 0
    //     },
    //     // language: {
    //     //     fixedColumns: {
    //     //         button: 'Sticky Columns'
    //     //     }
    //     // },
    //     // buttons:[
    //     //     'fixedColumns'
    //     // ]
    // });

    // $.ajax(tsAjaxUrl, {
    //     cache: false,
    //     dataType: "json",
    //     error: error,
    //     success: success
    // }).done(function (result) {
    //     console.log(result);
    // })

    // $('#datatable').DataTable({
    //     processing: true,
    //     serverSide: true,
    //     ajax: 'data/obj.txt',
    //     columns: [
    //         { data: 'id' },
    //         { data: 'name' },
    //         { data: 'daysTo' },
    //     ]
    // });
    // table_datatable_params = {
    //     "columns": [
    //         {
    //             "data": "id"
    //         },
    //         {
    //             "data": "name"
    //         },
    //         {
    //             "defaultContent": "",
    //             "orderable": false
    //         }
    //     ],
    //     "order": [
    //         [
    //             0,
    //             "desc"
    //         ]
    //     ]
    // };
    // table_datatable.dataTable(table_datatable_params);

    // console.log("timestamp makeEditable end");

});

function updateTableByData(data) {
    // console.log("updateTableByData");
    // console.log(data);
    // ctx.datatableApi.clear().rows.add(data).draw();
}


