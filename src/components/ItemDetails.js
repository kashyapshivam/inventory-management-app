import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { updateInventoryItem, deleteInventoryItem } from '../redux/actions';


const ItemDetails = ({ item }) => {
  const [isEditMode, setIsEditMode] = useState(false);
  const [editedItem, setEditedItem] = useState(item);
  const dispatch = useDispatch();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedItem((prevItem) => ({
      ...prevItem,
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    dispatch(updateInventoryItem(item.id, editedItem));
    setIsEditMode(false);
  };

  const handleDelete = () => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      dispatch(deleteInventoryItem(item.id));
    }
  };

  return (
    <div>
      {isEditMode ? (
        <div>
          <label>
            Product Name:
            <input type="text" name="productName" value={editedItem.productName} onChange={handleInputChange} />
          </label>
          {/* Add other input fields for Quantity, Category, Price per Unit, Shelf Number, and Vendor Link */}
          <button onClick={handleUpdate}>Save</button>
          <button onClick={() => setIsEditMode(false)}>Cancel</button>
        </div>
      ) : (
        <div>
          <p>Product Name: {item.productName}</p>
          {/* Display other item details here */}
          <button onClick={() => setIsEditMode(true)}>Edit</button>
          <button onClick={handleDelete}>Delete</button>
        </div>
      )}
      <hr />
    </div>
  );
};

export default ItemDetails;
