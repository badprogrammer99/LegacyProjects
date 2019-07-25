<template>
  <b-row>
    <b-col md="10" offset-md="1">
      <vue-snotify></vue-snotify>
      <b-card no-body>
        <b-tabs card>
          <b-tab title="Criar Inquérito" active>
            <b-form @submit.prevent="createInquiry">
              <b-form-group label="Nome" label-for="nome_inquerito">
                <b-form-input
                  id="nome_inquerito"
                  type="text"
                  required
                  placeholder="Nome do Inquérito"
                  v-model="inquiryName"
                ></b-form-input>
              </b-form-group>
              <b-button type="submit" variant="success">
                <i class="fas fa-angle-double-right"></i> Criar Inquérito
              </b-button>
            </b-form>
          </b-tab>
          <b-tab title="Editar Inquérito">
            <b-form @submit.prevent="editInquiry">
              <b-form-group
                label="Seleccione o Inquérito que deseja editar: "
                label-for="select_inquiry"
              >
                <b-form-select
                  id="select_inquiry"
                  :options="inquiries"
                  required
                  value-field="id"
                  text-field="name"
                  v-model="inquiryId"
                ></b-form-select>
              </b-form-group>
              <b-button type="submit" variant="success">
                <i class="fas fa-edit"></i> Editar Inquérito
              </b-button>
            </b-form>
          </b-tab>
          <b-tab title="Eliminar Inquérito">
            <b-form @submit.prevent="deleteInquiry">
              <b-form-group
                label="Seleccione o Inquérito que deseja eliminar: "
                label-for="delete_inquiry"
              >
                <b-form-select
                  id="delete_inquiry"
                  :options="inquiries"
                  required
                  value-field="id"
                  text-field="name"
                  v-model="inquiryId"
                ></b-form-select>
              </b-form-group>
              <b-button type="submit" variant="danger">
                <i class="fas fa-trash-alt"></i> Eliminar Inquérito
              </b-button>
            </b-form>
          </b-tab>
          <b-tab title="Gerar ficheiro de informações">
            <b-form @submit.prevent="generateInfo">
              <b-form-group
                label="Seleccione o Inquérito para o qual deseja gerar um ficheiro de informações: "
                label-for="generate_info"
              >
                <b-form-select
                  id="generate_info"
                  :options="inquiries"
                  required
                  value-field="id"
                  text-field="name"
                  v-model="inquiryId"
                ></b-form-select>
              </b-form-group>
              <b-button type="submit" variant="success">
                <i class="far fa-file-pdf"></i> Gerar PDF
              </b-button>
            </b-form>
          </b-tab>
        </b-tabs>
      </b-card>
    </b-col>
  </b-row>
</template>

<script>
export default {
  data() {
    return {
      inquiries: [],
      inquiryId: null,
      inquiriesURL: this.$attrs.inquiriesBaseURL,
      inquiryName: ""
    };
  },

  created() {
    this.fetchInquiries();
  },

  methods: {
    fetchInquiries: function() {
      window.axios.get(this.inquiriesURL).then(
        response => {
          this.inquiries = response.data.data;
        },
        error => {
          this.showErrorToast();
        }
      );
    },

    createInquiry: function() {
      window.axios
        .post(this.inquiriesURL, {
          inquiry_name: this.inquiryName
        })
        .then(
          response => {
            let inquiryId = response.data.id;
            this.$router.push({ path: `/inquiry/${inquiryId}` });
          },
          error => {
            this.showErrorToast();
          }
        );
    },

    editInquiry: function() {
      let inquiryId = this.inquiryId;
      this.$router.push({ path: `/inquiry/${inquiryId}` });
    },

    deleteInquiry: function() {
      window.axios.delete(this.inquiriesURL + this.inquiryId).then(
        response => {
          this.$snotify.success("O inquérito foi eliminado com sucesso!", {
            showProgressBar: false
          });
          this.fetchInquiries();
        },
        error => {
          this.showErrorToast();
        }
      );
    },

    generateInfo: function() {
      window.axios
        .post(this.inquiriesURL + "generate-inquiry-pdf", {
          id: this.inquiryId
        })
        .then(response => {
          
        }),
        error => {
          this.showErrorToast();
        };
    }
  }
};
</script>