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

             if (action === "bill") {
               var id = params.node.data.id;

               fetch('http://localhost:8080/dinehouse/api/v1/web-ui/bill/'+id, {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(request)
               })
               .then(response => response.json())
               .then(response => console.log(JSON.stringify(response)))
               .then(response => location.replace("http://localhost:8080/dinehouse/api/v1/orders.html"))
             }
             if (action === "update") {
               params.api.stopEditing(false);

               var request = {   orderId: params.node.data.id,
                                 userId: getCookie('userId'),
                                 method: params.node.data.paymentMethod,
                                 type:  "Order",
                                 amount: params.node.data.payableAmount,
                                 description: params.node.data.description
                             }

               fetch('http://localhost:8080/dinehouse/api/v1/web-ui/order-payment', {
                   method: 'POST',
                   headers: {
                       'Accept': 'application/json',
                       'Content-Type': 'application/json'
                   },
                   body: JSON.stringify(request)
               })
                .then(response => response.json())
               .then(response => console.log(JSON.stringify(response)))
               .then(response => location.replace("http://localhost:8080/dinehouse/api/v1/orders.html"))
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
                headerName: "OrderId",
                field: "id",
                editable: false
           },
           {
               headerName: "Location",
               field: "address",
               editable: false
           },
           {
               field: "type",
               editable: false
           },
           {
               field: "servedBy",
               editable: false
           },
           {
                headerName: "price",
                field: "payableAmount",
                editable: true
           },
           {
                headerName: "Payment Options",
                field: "paymentMethod",
                editable: true,
                cellEditor:'agSelectCellEditor',
                cellEditorParams: {
                   values: ['PhonePe', 'ZomatoPay', 'Cash', 'Card', 'Paytm', 'Pending']
                }
           },
           {
                field: "status",
                editable: true,
                cellEditor:'agSelectCellEditor',
                cellEditorParams: {
                      values: ['OPEN', 'CANCELLED', 'PENDING', 'DECLINED', 'PREPARING', 'DELIVERED', 'BILL_GENERATED','PAID']
                }
           },
           {
                field: "description",
                editable: true,
                cellEditor: 'agTextCellEditor'
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
         defaultColDef: {editable: true, sortable: true, filter: true},
         rowSelection: 'multiple',
         animateRows: true
       };

  const eGridDiv = document.getElementById("ordersView");
  new agGrid.Grid(eGridDiv, gridOptions);

  fetch("http://localhost:8080/dinehouse/api/v1/web-ui/orders")
  .then(response => response.json())
  .then(data => {
     gridOptions.api.sizeColumnsToFit();
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
            class="action-button bill"
            data-action="bill">
               bill
          </button>
          `;
    }
    return eGui;
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