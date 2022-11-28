var columns = [];

function getDT1() {
    $.ajax({
        url: "/ssp/objects.php",
        success: function (data) {
            data = JSON.parse(data);
            columnNames = Object.keys(data.data[0]);
            for (var i in columnNames) {
                columns.push({data: columnNames[i],
                    title: capitalizeFirstLetter(columnNames[i])});
            }
            $('#example').DataTable( {
                data: data.data,
                columns: columns
            } );
        }
    });
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

$(document).ready(function() {


    initTableByData();


} );

