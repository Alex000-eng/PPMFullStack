import axios from "axios";
import {
  GET_ERRORS,
  GET_BACKLOG,
  GET_PROJECT_TASK,
  DELETE_PROJECT_TASK,
} from "./types";

export const addProjectTask = (backlog_id, project_task, history) => async (
  dispatch
) => {
  try {
    await axios.post(`/api/backlog/${backlog_id}`, project_task);
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getBacklog = (backlog_id) => async (dispatch) => {
  try {
    const res = await axios.get(`/api/backlog/${backlog_id}`);
    dispatch({
      type: GET_BACKLOG,
      payload: res.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getProjectTask = (backlog_id, projectSequence, history) => async (
  dispatch
) => {
  try {
    const res = await axios.get(
      `/api/backlog/${backlog_id}/${projectSequence}`
    );
    dispatch({
      type: GET_PROJECT_TASK,
      payload: res.data,
    });
  } catch (error) {
    history.push("/dashBoard");
  }
};

export const updateProjectTask = (
  backlog_id,
  projectSequence,
  project_task,
  history
) => async (dispatch) => {
  try {
    await axios.patch(
      `/api/backlog/${backlog_id}/${projectSequence}`,
      project_task
    );
    history.push(`/projectBoard/${backlog_id}`);
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const deleteProjectTask = (backlog_id, projectSequence) => async (
  dispatch
) => {
  if (
    window.confirm(
      `You are deleting project task ${projectSequence} ,this operation can't be undone`
    )
  ) {
    await axios.delete(`/api/backlog/${backlog_id}/${projectSequence}`);
    dispatch({
      type: DELETE_PROJECT_TASK,
      payload: projectSequence,
    });
  }
};
