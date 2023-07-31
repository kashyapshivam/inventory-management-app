// src/redux/actions.js

import axios from 'axios';

export const FETCH_INVENTORY_SUCCESS = 'FETCH_INVENTORY_SUCCESS';
export const ADD_INVENTORY_ITEM = 'ADD_INVENTORY_ITEM';
export const UPDATE_INVENTORY_ITEM = 'UPDATE_INVENTORY_ITEM';
export const DELETE_INVENTORY_ITEM = 'DELETE_INVENTORY_ITEM';

export const fetchInventorySuccess = (inventory) => {
  return { type: FETCH_INVENTORY_SUCCESS, payload: inventory };
};

const config = {
  headers:{
    Accept: 'application/json',
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  }
};

export const fetchInventory = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('http://localhost:8080/api/inventory',config);
      dispatch(fetchInventorySuccess(response.data));
    } catch (error) {
      console.error('Error fetching inventory:', error);
    }
  };
};

export const addInventoryItem = (newItem) => {
  return async (dispatch) => {
    try {
      const response = await axios.post('http://localhost:8080/api/inventory', newItem,config);
      dispatch({ type: ADD_INVENTORY_ITEM, payload: response.data });
    } catch (error) {
      console.error('Error adding item:', error);
    }
  };
};

export const updateInventoryItem = ( updatedItem) => {
  return async (dispatch) => {
    try {
      const response = await axios.put(`http://localhost:8080/api/inventory/update`, updatedItem);
      dispatch({ type: UPDATE_INVENTORY_ITEM, payload: { updatedItem: response.data } });
    } catch (error) {
      console.error('Error updating item:', error);
    }
  };
};

export const deleteInventoryItem = (itemId) => {
  return async (dispatch) => {
    try {
      await axios.delete(`http://localhost:8080/api/inventory/${itemId}`,config);
      dispatch({ type: DELETE_INVENTORY_ITEM, payload: itemId });
    } catch (error) {
      console.error('Error deleting item:', error);
    }
  };
};
