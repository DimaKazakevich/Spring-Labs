$(document).ready(function(){
/*  'beforeChanges' variable contains value of selected table row by every click in it.
    When focus is out selected row(focus was in selected row  because 'tr' tag contains 'contenteditable' attribute)
    'changes' variable contains value of selected table row which(value) could be changed.

    So in focusout event handler two values('beforeChanges' and 'changes') are compared and
    if they aren't equal request is sent to the server with aim of update collection.

    So in keydawn event handler i put 'beforeChanges' variale as 'selectedTableRow' parameter because
    variable contains value of selected table row by every click and
    if user press 'delete' button request is sent to the server where server looks for book which
    containts 'title' or 'author' equal beforeChanges value and is deleted book from collection.*/

    let beforeChanges;
    $('[contenteditable]').click(function(event) {
        beforeChanges = event.target.innerText;
    });

    $('[contenteditable]').focusout(function(event) {
        let changes = event.target.innerText;
        if(beforeChanges !== changes) {
            $.ajax({
                url: '/edit',
                type: 'PUT',
                data: { beforeChanges: beforeChanges, changes: changes}
            });
        }
    });

    $('[contenteditable]').keydown(function(event) {
        const key = event.key;
        if (key === "Delete") {
            $.ajax({
                url: '/deletebook',
                type: 'DELETE',
                data: {selectedTableRow: beforeChanges}
            });
            location.reload();
        }
    });

    $(".container tr").click(function() {
        $(this).addClass('selected').siblings().removeClass('selected');
    });
})