function updateToDoItem(row) {
    let id = $("#toDoItemId_" + row).val();
    let name = $("#toDoItemName_" + row).val();

    $.ajax({
        url: 'update',
        type: 'POST',
        data: 'id=' + id + '&name=' + name,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert('更新待办事项失败：' + errorThrown);
        },
        success: function (data, textStatus) {
            $("#toDoItemLabel_" + row).text(name);
            $("#toDoItem_" + row).removeClass("editing");
        }
    });
}