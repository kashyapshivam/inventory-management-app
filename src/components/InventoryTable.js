import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchInventory, updateInventoryItem, deleteInventoryItem } from '../redux/actions';
import Modal from 'react-modal';

const InventoryTable = () => {
  const [sortConfig, setSortConfig] = useState({ key: '', direction: '' });
  const [searchTerm, setSearchTerm] = useState('');
  const inventory = useSelector((state) => state.inventory);
  const dispatch = useDispatch();



  const [viewModalIsOpen, setViewModalIsOpen] = useState(false);
  const [updateModalIsOpen, setUpdateModalIsOpen] = useState(false);
  const [deleteModalIsOpen, setDeleteModalIsOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [updatedItemData, setUpdatedItemData] = useState(null);



  useEffect(() => {
    dispatch(fetchInventory());
  }, [dispatch]);


  useEffect(() => {
    dispatch(fetchInventory());
  }, [dispatch]);

  const handleSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
  };


  

  const sortedInventory = [...inventory].sort((a, b) => {
    if (sortConfig.direction === 'asc') {
      return a[sortConfig.key] > b[sortConfig.key] ? 1 : -1;
    } else if (sortConfig.direction === 'desc') {
      return a[sortConfig.key] < b[sortConfig.key] ? 1 : -1;
    }
    return 0;
  });

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const filteredInventory = sortedInventory.filter((item) =>
    item.productName.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const handleView = (item) => {
    setSelectedItem(item);
    setViewModalIsOpen(true);
  };

  const handleUpdate = (item) => {
    setSelectedItem(item);
    setUpdatedItemData(item);
    setUpdateModalIsOpen(true);
  };

  const handleDelete = (itemId) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      dispatch(deleteInventoryItem(itemId));
    }
  };

  const handleUpdateItem = async () => {
    // Dispatch action to update the item in the backend
    await dispatch(updateInventoryItem(updatedItemData));
    setUpdateModalIsOpen(false);
  };

  const handleDeleteItem = (itemId) => {
    // Dispatch action to delete the item in the backend
    dispatch(deleteInventoryItem(itemId));
    setDeleteModalIsOpen(false);
  };

  useEffect(() => {
    if (selectedItem) {
      const updatedItemFromStore = inventory.find((item) => item.id === selectedItem.id);
      setSelectedItem(updatedItemFromStore);
    }
  }, [inventory, selectedItem]);

  

  return (
    <div className="container">
      <h2>Inventory Table</h2>
      <input
        type="text"
        placeholder="Search by Product Name"
        value={searchTerm}
        onChange={handleSearch}
      />
      <table>
        <thead>
          <tr>
            <th onClick={() => handleSort('productName')}>
              Product Name {sortConfig.key === 'productName' && sortConfig.direction === 'asc' ? '▲' : '▼'}
            </th>
            <th onClick={() => handleSort('quantity')}>
              Quantity {sortConfig.key === 'quantity' && sortConfig.direction === 'asc' ? '▲' : '▼'}
            </th>
            <th onClick={() => handleSort('category')}>
              Category {sortConfig.key === 'category' && sortConfig.direction === 'asc' ? '▲' : '▼'}
            </th>
            <th onClick={() => handleSort('pricePerUnit')}>
              Price per Unit {sortConfig.key === 'pricePerUnit' && sortConfig.direction === 'asc' ? '▲' : '▼'}
            </th>
            <th onClick={() => handleSort('shelfNumber')}>
              Shelf Number {sortConfig.key === 'shelfNumber' && sortConfig.direction === 'asc' ? '▲' : '▼'}
            </th>
            <th>Vendor Link</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredInventory.map((item) => (
            <tr key={item.id}>
              <td>{item.productName}</td>
              <td>{item.quantity}</td>
              <td>{item.category}</td>
              <td>{item.pricePerUnit}</td>
              <td>{item.shelfNumber}</td>
              <td>
                <a href={item.vendorLink} target="_blank" rel="noopener noreferrer">
                  Visit Vendor
                </a>
              </td>
              <td>
                <div>
                  {/* View button */}
                  <button onClick={() => handleView(item)}>View</button>
                  {/* Edit button */}
                  <button onClick={() => handleUpdate(item)}>Edit</button>
                  {/* Delete button */}
                  <button onClick={() => handleDelete(item.id)}>Delete</button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>


      <Modal
        isOpen={viewModalIsOpen}
        onRequestClose={() => setViewModalIsOpen(false)}
        className="modal"
        overlayClassName="overlay"
      >
        {selectedItem && (
          <>
            <h2>View Inventory Item</h2>
            <p>Product Name: {selectedItem.productName}</p>
            <p>Quantity: {selectedItem.quantity}</p>
            <p>Category: {selectedItem.category}</p>
            <p>Price per Unit: {selectedItem.pricePerUnit}</p>
            <p>Shelf Number: {selectedItem.shelfNumber}</p>
            <p>Vendor Link: <a href={selectedItem.vendorLink} target="_blank" rel="noopener noreferrer">Visit Vendor</a></p>
            <button onClick={() => setViewModalIsOpen(false)}>Close</button>
          </>
        )}
      </Modal>

       {/* Update Modal */}
       <Modal
        isOpen={updateModalIsOpen}
        onRequestClose={() => setUpdateModalIsOpen(false)}
        className="modal"
        overlayClassName="overlay"
      >
        {updatedItemData && (
          <>
            <h2>Update Inventory Item</h2>
            {/* Add form fields for updating item */}
            <form onSubmit={handleUpdateItem}>
              <div>
                <label>Product Name:</label>
                <input
                  type="text"
                  value={updatedItemData.productName}
                  onChange={(e) => setUpdatedItemData({ ...updatedItemData, productName: e.target.value })}
                />
              </div>
              <div>
                <label>Quantity:</label>
                <input
                  type="number"
                  value={updatedItemData.quantity}
                  onChange={(e) => setUpdatedItemData({ ...updatedItemData, quantity: e.target.value })}
                />
              </div>
              {/* Add more form fields for other properties */}
              <div>
                <button type="button" onClick={() => setUpdateModalIsOpen(false)}>Cancel</button>
                <button type="submit" onClick={handleUpdateItem}>Update Item</button>
              </div>
            </form>
          </>
        )}
      </Modal>

      {/* Delete Modal */}
      <Modal
        isOpen={deleteModalIsOpen}
        onRequestClose={() => setDeleteModalIsOpen(false)}
        className="modal"
        overlayClassName="overlay"
      >
        {selectedItem && (
          <>
            <h2>Delete Inventory Item</h2>
            <p>Are you sure you want to delete this item?</p>
            <button onClick={() => setDeleteModalIsOpen(false)}>Cancel</button>
            <button onClick={() => handleDeleteItem(selectedItem.id)}>Delete</button>
          </>
        )}
      </Modal>

    </div>
  );
};

export default InventoryTable;
