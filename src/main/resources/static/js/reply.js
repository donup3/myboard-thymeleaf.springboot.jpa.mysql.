var replyManger = (function () {
    var getAll = function (obj, callback) {
        console.log("get All........");

        var bno = obj.bno;
        var page = obj.page || 1;
        console.log(bno+"dd"+page);
        $.getJSON('/replies/pages/'+bno+"/"+page,callback);
    };

    var add = function (obj, callback) {
        console.log("add........");
        var bno = obj.bno;
        var page = obj.page || 1;

        $.ajax({
            type:'post',
            url: '/replies/pages/'+bno+"/"+page,
            data: JSON.stringify(obj),
            dataType: 'json',
            contentType: "application/json",
            success:callback
        });

    };

    var update = function (obj, callback) {
        console.log("update........");

        var bno = obj.bno;
        var page = obj.page || 1;

        $.ajax({
            type:'put',
            url: '/replies/pages/'+bno+"/"+page,
            data: JSON.stringify(obj),
            dataType: 'json',
            contentType: "application/json",
            success:callback
        });
    };

    var remove = function (obj, callback) {
        console.log("get remove........");
        var bno = obj.bno;
        var rno=obj.rno;

        $.ajax({
            type:'delete',
            url: '/replies/pages/'+bno+"/"+rno,
            dataType: 'json',
            contentType: "application/json",
            success:callback
        });
    };

    return {
        getAll: getAll,
        add: add,
        update: update,
        remove: remove
    }

})();