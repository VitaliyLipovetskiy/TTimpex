let form;

function getCookie(cookieName) {
    let results = document.cookie.match('(^|;) ?' + cookieName + '=([^;]*)(;|$)');

    if (results)
        return (unescape(results[2]));
    else
        return null;
}

function setCookie(name, value, expY, expM, expD, path, domain, secure) {
    let cookieString = name + "=" + escape (value);
    if (expY) {
        let expires = new Date(expY, expM, expD);
        cookieString += "; expires=" + expires.toGMTString();
    }

    if (path)
        cookieString += "; path=" + escape (path);

    if (domain)
        cookieString += "; domain=" + escape (domain);

    if (secure)
        cookieString += "; secure";

    document.cookie = cookieString;
}

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
    addRow();
    // updateDepartments();
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