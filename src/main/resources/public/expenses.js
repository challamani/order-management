const gridOptions = {
        suppressClickEdit: true,
        suppressHorizontalScroll: true,
        onCellClicked(params) {
          if (params.column.colId === "action" && params.event.target.dataset.action) {
            let action = params.event.target.dataset.action;

            if (action === "edit") {
              params.api.startEditingCell({
                rowIndex: params.node.rowIndex,
                colKey: params.columnApi.getDisplayedCenterColumns()[0].colId
              });
            }

            if (action === "delete") {
              params.api.applyTransaction({
                remove: [params.node.data]
              });
              var id = params.node.data.id;

              fetch('http://localhost:8080/dinehouse/api/v1/web-ui/tran-record/'+id, {
                 method: 'DELETE',
                 headers: {
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'
                 },
                 body: JSON.stringify(request)
              })
              .then(response => response.json())
              .then(response => console.log(JSON.stringify(response)))
              .then(response => location.replace("http://localhost:8080/dinehouse/api/v1/expenses.html"))
            }

            if (action === "update") {
              params.api.stopEditing(false);

              var request = {   id:params.node.data.id,
                                userId: getCookie('userId'),
                                method: params.node.data.paymentMethod,
                                type:  params.node.data.tranGroup,
                                amount: params.node.data.amount,
                                description: params.node.data.description
                            }

              fetch('http://localhost:8080/dinehouse/api/v1/web-ui/tran-record', {
                  method: 'POST',
                  headers: {
                      'Accept': 'application/json',
                      'Content-Type': 'application/json'
                  },
                  body: JSON.stringify(request)
              })
              .then(response => response.json())
              .then(response => console.log(JSON.stringify(response)))
              .then(response => location.replace("http://localhost:8080/dinehouse/api/v1/expenses.html"))
            }

            if (action === "cancel") {
              params.api.stopEditing(true);
            }
          }
        },
      onRowEditingStarted: (params) => {
          params.api.refreshCells({
            columns: ['action'],
            rowNodes: [params.node],
            force: true
          });
        },
        onRowEditingStopped: (params) => {
          params.api.refreshCells({
            columns: ['action'],
            rowNodes: [params.node],
            force: true
          });
        },
      columnDefs: [
           {
                field: "id",
                editable: false
           },
           {
             field: "amount",
             editable: true,
             cellEditor: 'agTextCellEditor'
           },
           {
                field: "paymentMethod",
                editable: true,
                cellEditor:'agSelectCellEditor',
                cellEditorParams: {
                      values: ['PhonePe', 'ZomatoPay', 'Cash', 'Card', 'Paytm', 'Pending']
                }
           },
           {
                field: "tranGroup",
                editable: true,
                cellEditor:'agSelectCellEditor',
                cellEditorParams: {
                     values: ['Order', 'Wages', 'Rent', 'Chicken', 'Gas', 'Vegetables','Grocery', 'Rice', 'Maintenance', 'AdvancePay', 'Other']
                }
           },
           {
              field: "description",
              editable: true,
              cellEditor: 'agTextCellEditor'
           },
           {
                headerName: "Recorded By",
                field: "userId",
                editable: false
           },
           {
                 headerName: "action",
                 minWidth: 150,
                 cellRenderer: actionCellRenderer,
                 editable: false,
                 colId: "action"
           }
         ],
         editType: "fullRow",
         defaultColDef: { editable: true, sortable: true, filter: true},
         rowSelection: 'multiple',
         animateRows: true
       };

const eGridDiv = document.getElementById("expensesView");
new agGrid.Grid(eGridDiv, gridOptions);

fetch("http://localhost:8080/dinehouse/api/v1/web-ui/transactions/dr")
.then(response => response.json())
.then(data => {
   gridOptions.api.setRowData(data);
});

function actionCellRenderer(params) {
  let eGui = document.createElement('div');

  let editingCells = params.api.getEditingCells();
  let isCurrentRowEditing = editingCells.some((cell) => {
    return cell.rowIndex === params.node.rowIndex;
  });

  if (isCurrentRowEditing) {
    eGui.innerHTML = `
        <button
          class="action-button update"
          data-action="update">
               update
        </button>
        <button
          class="action-button cancel"
          data-action="cancel">
               cancel
        </button>
        `;
  } else {
    eGui.innerHTML = `
        <button
          class="action-button edit"
          data-action="edit">
             edit
          </button>
        <button
          class="action-button delete"
          data-action="delete">
             delete
        </button>
        `;
  }
  return eGui;
}

function createNewRowData() {
  const newData = {
    amount: '0',
    paymentMethod: 'Cash',
    tranGroup: 'Wages',
    description: 'Description'
  };
  return newData;
}

function getRowData() {
  const rowData = [];
  gridOptions.api.forEachNode(function (node) {
    rowData.push(node.data);
  });
  console.log('Row Data:');
  console.table(rowData);
}


function addItems(addIndex) {
  const newItems = [createNewRowData()];
  const res = gridOptions.api.applyTransaction({
    add: newItems,
    addIndex: addIndex,
  });
  printResult(res);
}

function updateItems() {
  const itemsToUpdate = [];
  gridOptions.api.forEachNodeAfterFilterAndSort(function (rowNode, index) {
    if (index >= 2) {
      return;
    }
    const data = rowNode.data;
    data.price = Math.floor(Math.random() * 20000 + 20000);
    itemsToUpdate.push(data);
  });
  const res = gridOptions.api.applyTransaction({ update: itemsToUpdate });
  printResult(res);
}

function onRemoveSelected() {
  const selectedData = gridOptions.api.getSelectedRows();
  const res = gridOptions.api.applyTransaction({ remove: selectedData });
  printResult(res);
}

function printResult(res) {
  console.log('---------------------------------------');
  if (res.add) {
    res.add.forEach(function (rowNode) {
      console.log('Added Row Node', rowNode);
    });
  }
  if (res.remove) {
    res.remove.forEach(function (rowNode) {
      console.log('Removed Row Node', rowNode);
    });
  }
  if (res.update) {
    res.update.forEach(function (rowNode) {
      console.log('Updated Row Node', rowNode);
    });
  }
}

function getCookie(cname) {
  let name = cname + "=";
  let ca = document.cookie.split(';');
  for(let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}