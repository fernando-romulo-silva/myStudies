const URL_BASE = "http://localhost:3000";

const converterStringParaData = (dataString) => {
  const [ano, mes, dia] = dataString.split("-");
  return new Date(Date.UTC(ano, mes - 1, dia));
};

const api = {
  async buscarPensamentos() {
    try {
      const response = await axios.get(`${URL_BASE}/pensamentos`);
      const pensamentos = await response.data;

      return pensamentos.map((pensamento) => {
        return {
          ...pensamento,
          data: new Date(pensamento.data),
        };
      });
    } catch {
      alert("Erro ao buscar pensamentos");
      throw error;
    }
  },

  async salvarPensamento(pensamento) {
    try {
      const data = converterStringParaData(pensamento.data);

      const response = await axios.post(`${URL_BASE}/pensamentos`, {
        ...pensamento,
        data: data.toISOString(),
      });

      return await response.data;
    } catch {
      alert("Erro ao salvar pensamento");
      throw error;
    }
  },

  async buscarPensamentoPorId(id) {
    try {
      const response = await axios.get(`${URL_BASE}/pensamentos/${id}`);
      const pensamento = await response.data;

      return { ...pensamento, data: new Date(pensamento.data) };
    } catch {
      alert("Erro ao buscar pensamento");
      throw error;
    }
  },

  async editarPensamento(pensamento) {
    try {
      const response = await axios.put(
        `${URL_BASE}/pensamentos/${pensamento.id}`,
        pensamento
      );
      return await response.data;
    } catch {
      alert("Erro ao editar pensamento");
      throw error;
    }
  },

  async excluirPensamento(id) {
    try {
      const response = await axios.delete(`${URL_BASE}/pensamentos/${id}`);
    } catch {
      alert("Erro ao excluir um pensamento");
      throw error;
    }
  },

  async buscarPensamentosPorTermo(termo) {
    try {
      const pensamentos = await this.buscarPensamentos();

      const termoEmMinusculuas = termo.toLowerCase();

      const pensamentosFiltratados = pensamentos.filter((pensamento) => {
        return (
          pensamento.conteudo.toLowerCase().includes(termoEmMinusculuas) ||
          pensamento.autoria.toLowerCase().includes(termoEmMinusculuas)
        );
      });

      return pensamentosFiltratados;
    } catch (error) {
      alert("Erro ao filtra pensamentos");
      throw error;
    }
  },

  async atualizarFavorito(id, favorito) {
    try {
      const response = await axios.patch(`${URL_BASE}/pensamentos/${id}`, {
        favorito,
      });
      return response;
    } catch (error) {
      alert("Erro ao atualizar favorito");
      throw error;
    }
  },
};

export default api;
