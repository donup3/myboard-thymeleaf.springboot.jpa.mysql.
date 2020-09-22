var replyManger = (function () {
    var getAll = function (obj, callback) {
        console.log("get All........");

        var bno = obj.bno;
        var page = obj.page || 1;
        console.log(bno+"dd"+page);
        $.getJSON('/replies/pages/'+bno+"/"+page,callback);
    }
    var add = function (obj, callback) {
        console.log("add........");
    }
    var update = function (obj, callback) {
        console.log("update........");
    }
    var remove = function (obj, callback) {
        console.log("get remove........");
    }

    return {
        getAll: getAll,
        add: add,
        update: update,
        remove: remove
    }

})();