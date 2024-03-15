import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

export default class AllProfile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      users: [],
    };
  }

  async componentDidMount() {
    const users = await AuthService.getAll();
    console.log(users);

    if (!users) this.setState({ redirect: "/home" });
    this.setState({ users: users, userReady: true });
  }

  _renderProfiles(profile, index) {
    return (
      <li key={index}>
        {profile.nome}/{profile.email}
      </li>
    );
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />;
    }

    const { users } = this.state;

    return (
      <div>
        <h1>Todos os perfis</h1>
        <ul>
          {users ? users.map(this._renderProfiles) : "Sem perfis para mostrar"}
        </ul>
      </div>
    );
  }
}
