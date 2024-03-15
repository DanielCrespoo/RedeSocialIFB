import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

export default class User extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: null,
      url: window.location.href,
    };
  }

  async componentDidMount() {
    const { url } = this.state;
    const id = url.substring(url.length - 1);
    const currentUser = await AuthService.getUserById(parseInt(id));

    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true });
  }

  _renderProfile(currentUser) {
    return (
      <div>
        <header className="jumbotron">
          <h3>
            Perfil do <strong>{currentUser.nome}</strong>
          </h3>
        </header>
        <p>
          <strong>Id:</strong> {currentUser.id}
        </p>
        <p>
          <strong>Nome:</strong> {currentUser.nome}
        </p>
        <p>
          <strong>Email:</strong> {currentUser.email}
        </p>
      </div>
    );
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />;
    }

    const { currentUser } = this.state;

    return (
      <div className="container">
        {this.state.userReady ? this._renderProfile(currentUser) : null}
      </div>
    );
  }
}
