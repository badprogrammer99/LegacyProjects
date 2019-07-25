<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <style>
          .page-break {
            page-break-after: always;
          }
          #report-container * {
            padding-left: 10px;
            padding-right: 10px;
          }

          #report-container hr {
            height: 1px;
            background-color: black;
          }

          .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
          }

          .header h2 {
            font-family: sans-serif;
          }

          .header .report-information .information-box {
            display: grid;
            padding-bottom: 5px;
            font-family: 'Trebuchet MS';
          }

          .content {
            display: flex;
            flex-direction: column;
            padding-top: 10px;
            font-family: 'Arial', sans-serif;
          }

          .content .questionnaire-list {
            padding-left: 20px !important;
          }

          .content .questionnaire-box {
            border-top: 1px solid black;
            border-bottom: 1px solid black;
            padding-left: 30px !important;
            margin-bottom: 20px;
          }

          .content .questionnaire-box .table {
            margin: 0 0 40px 0;
            width: 100%;
            display: table;
          }

          .content .questionnaire-box .table .row {
            display: table-row;
            background: #f6f6f6;
          }

          .content .questionnaire-box .table .row:nth-of-type(odd) {
            background: #e9e9e9;
          }

          .content .questionnaire-box .table .row.header {
            font-weight: 900;
            color: #B5BAD0;
            background: #2980B9;
          }

          .content .questionnaire-box .table .cell {
            padding: 6px 12px !important;
            display: table-cell;
          }
        </style>
    </head>
    <body>
        <div id="report-container">
            <hr>
            <div class="header">
                <img src="/public/img/logotipo_hospital.jpg" alt="Logótipo do Hospital de Santarém">
                <h2>Relatório do Inquérito {{ $inquiry->name }} do hospital de Santarém</h2>
                <div class="report-information">
                    <div class="information-box">
                        Gerado a {{ Date::now()->format('d') }} de {{ Date::now()->format('F') }}, às {{ Date::now()->format('H:i:s') }}
                    </div>
                    <div class="information-box">
                        Pelo administrador {{ $user }}
                    </div>
                </div>
            </div>
            <hr>
            <div class="content">
                <div class="inquiry-box">
                    <h4>Inquérito de {{ $inquiry->name }} </h4>
                    <p>Este inquérito contém {{ $inquiry->questionnaries->count() }} questionários.</p>
                    <p>Estes são: </p>
                    <div class="questionnaire-list">
                        <ul>
                            @foreach ($inquiry->questionnaries as $questionnaire)
                              <li>{{ $questionnaire->name }}</li>
                            @endforeach
                        </ul>
                    </div>
                    <p>A informação de cada questionário referente a este inquérito está discriminada imediatamente abaixo: </p>
                    @foreach ($inquiry->questionnaries as $questionnaire)
                      <div class="questionnaire-box">
                          <h4>Questionário de {{ $questionnaire->name }}</h4>
                          <p>Este questionário contém {{ $questionnaire->questions->count() }} questões.</p>
                          <p>A informação sobre as mesmas está distribuída nesta tabela: </p>
                          <div class="table">
                              <div class="row header">
                                  <div class="cell">Identificador númerico</div>
                                  <div class="cell">Nome</div>
                                  <div class="cell">Descrição</div>
                                  <div class="cell">Número de respostas possíveis</div>
                                  <div class="cell">Lista de respostas possíveis</div>
                              </div>
                              @foreach ($questionnaire->questions as $question)
                                <div class="row">
                                    <div class="cell">{{ $question->id }}</div>
                                    <div class="cell">{{ $question->name }}</div>
                                    <div class="cell">{{ $question->description }}</div>
                                    <div class="cell">{{ $question->possible_answers }}</div>
                                    <div class="cell">
                                      @foreach ($question->PossibleAnswerNames->possible_answer_names as $possible_answer_name)
                                          @if (gettype($possible_answer_name) === 'string')
                                              {{ $possible_answer_name . ($loop->last ? '' : ',') }}
                                          @else
                                              Opção composta:
                                              @foreach ($possible_answer_name as $composite_possible_answer)
                                                {{ $composite_possible_answer . ($loop->last ? '' : ',') }}
                                              @endforeach
                                          @endif
                                      @endforeach
                                    </div>
                                </div>
                              @endforeach
                          </div>
                      </div>
                      <div class="page-break"></div>
                    @endforeach
                </div>
            </div>
        </div>
    </body>
</html>