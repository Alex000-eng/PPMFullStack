import React, { Component } from "react";
import { Link } from "react-router-dom";
import ProjectTask from "./ProjectTask/ProjectTask";
class Backlog extends Component {
  render() {
    const { project_tasks } = this.props;

    const tasks = project_tasks.map((project_task) => (
      <ProjectTask key={project_task} project_task={project_task} />
    ));

    let toDoItems = [];
    let inProgressItems = [];
    let doneItems = [];

    tasks.forEach((task) => {
      if (task.props.project_task.status == "TO_DO") {
        toDoItems.push(task);
      }
      if (task.props.project_task.status == "DONE") {
        doneItems.push(task);
      }
      if (task.props.project_task.status == "IN_PROGRESS") {
        inProgressItems.push(task);
      }
    });

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {toDoItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgressItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {doneItems}
          </div>
        </div>
      </div>
    );
  }
}
export default Backlog;
