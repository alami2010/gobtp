import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { EntityState, IQueryParams, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ITravailSupplementaire, defaultValue } from 'app/shared/model/travail-supplementaire.model';

const initialState: EntityState<ITravailSupplementaire> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/travail-supplementaires';

// Actions

export const getEntities = createAsyncThunk(
  'travailSupplementaire/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `page=${page}&size=${size}&sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<ITravailSupplementaire[]>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const getEntity = createAsyncThunk(
  'travailSupplementaire/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ITravailSupplementaire>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'travailSupplementaire/create_entity',
  async (entity: ITravailSupplementaire, thunkAPI) => {
    const result = await axios.post<ITravailSupplementaire>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'travailSupplementaire/update_entity',
  async (entity: ITravailSupplementaire, thunkAPI) => {
    const result = await axios.put<ITravailSupplementaire>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'travailSupplementaire/partial_update_entity',
  async (entity: ITravailSupplementaire, thunkAPI) => {
    const result = await axios.patch<ITravailSupplementaire>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'travailSupplementaire/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<ITravailSupplementaire>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const TravailSupplementaireSlice = createEntitySlice({
  name: 'travailSupplementaire',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = TravailSupplementaireSlice.actions;

// Reducer
export default TravailSupplementaireSlice.reducer;