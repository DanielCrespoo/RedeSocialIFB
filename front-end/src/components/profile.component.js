import React, { Component } from "react";
import { Navigate } from "react-router-dom";
import AuthService from "../services/auth.service";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: { username: "" },
    };
  }

  async componentDidMount() {
    const currentUser = await AuthService.getCurrentUser();

    if (!currentUser) this.setState({ redirect: "/home" });
    this.setState({ currentUser: currentUser, userReady: true });
  }

  render() {
    if (this.state.redirect) {
      return <Navigate to={this.state.redirect} />;
    }

    const { currentUser } = this.state;

    return (
      <div className="container">
        {this.state.userReady ? (
          <div>
            <header className="jumbotron">
              <h3>
                <strong>{currentUser.nome}</strong> Perfil do usuário logado
              </h3>
            </header>
            <p>
              <strong>Id:</strong> {currentUser.data.id}
            </p>
            <p>
              <strong>Nome:</strong> {currentUser.data.nome}
            </p>
            <p>
              <strong>Email:</strong> {currentUser.data.email}
            </p>
          </div>
        ) : null}
      </div>
    );
  }
}
