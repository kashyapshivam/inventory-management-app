// src/redux/reducers.js

import { combineReducers } from 'redux';
import {
  FETCH_INVENTORY_SUCCESS,
  ADD_INVENTORY_ITEM,
  UPDATE_INVENTORY_ITEM,
  DELETE_INVENTORY_ITEM,
} from './actions';

const inventoryReducer = (state = [], action) => {
  switch (action.type) {
    case FETCH_INVENTORY_SUCCESS:
      return action.payload;

      case UPDATE_INVENTORY_ITEM:
        const { itemId, updatedItem } = action.payload;
        return state.map((item) => (item.id === itemId ? updatedItem : item));
  
    // Add cases for other actions to update the state accordingly
    default:
      return state;
  }
  
};




export default combineReducers({
  inventory: inventoryReducer,
});
