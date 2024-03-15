import axios from "axios";

const API_URL = "http://localhost:8080/";

class AuthService {
  login(email, senha) {
    return axios
      .post(API_URL + "login", {
        email,
        senha,
      })
      .then((response) => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(nome, telefone, empresa, email, senha) {
    return axios.post(
      API_URL + "perfis",
      {
        nome,
        telefone: parseInt(telefone),
        empresa,
        email,
        senha,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  }

  getCurrentUser() {
    let token = "";

    if (localStorage.getItem("user")) {
      token =
        "Bearer " +
        localStorage
          .getItem("user")
          .substring(10, localStorage.getItem("user").length)
          .substring(0, 161);
    }

    return axios.get(API_URL + "perfil", {
      headers: {
        Authorization: token,
      },
    });
  }

  getUserById(id) {
    let token = "";

    if (localStorage.getItem("user")) {
      token =
        "Bearer " +
        localStorage
          .getItem("user")
          .substring(10, localStorage.getItem("user").length)
          .substring(0, 161);
    }

    return axios
      .get(API_URL + "perfis/" + id, {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => response.data);
  }

  getAll() {
    let token = "";

    if (localStorage.getItem("user")) {
      token =
        "Bearer " +
        localStorage
          .getItem("user")
          .substring(10, localStorage.getItem("user").length)
          .substring(0, 161);
    }

    return axios
      .get(API_URL + "perfis", {
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        return response.data;
      });
  }
}

const authService = new AuthService();

export default authService;
