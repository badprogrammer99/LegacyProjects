using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace QuestionarioC.Migrations
{
    public partial class InitialCreate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "AnswerChoices",
                columns: table => new
                {
                    AnswerChoiceId = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AnswerChoices", x => x.AnswerChoiceId);
                });

            migrationBuilder.CreateTable(
                name: "Inquiries",
                columns: table => new
                {
                    InquiryId = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Inquiries", x => x.InquiryId);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    UserId = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    PersonalId = table.Column<int>(nullable: false),
                    Name = table.Column<string>(nullable: true),
                    Password = table.Column<string>(nullable: true),
                    IsAdmin = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.UserId);
                });

            migrationBuilder.CreateTable(
                name: "Questionnaires",
                columns: table => new
                {
                    QuestionnaireId = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true),
                    InquiryId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Questionnaires", x => x.QuestionnaireId);
                    table.ForeignKey(
                        name: "FK_Questionnaires_Inquiries_InquiryId",
                        column: x => x.InquiryId,
                        principalTable: "Inquiries",
                        principalColumn: "InquiryId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserInquiries",
                columns: table => new
                {
                    UserId = table.Column<int>(nullable: false),
                    InquiryId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserInquiries", x => new { x.UserId, x.InquiryId });
                    table.ForeignKey(
                        name: "FK_UserInquiries_Inquiries_InquiryId",
                        column: x => x.InquiryId,
                        principalTable: "Inquiries",
                        principalColumn: "InquiryId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserInquiries_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Questions",
                columns: table => new
                {
                    QuestionId = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(nullable: true),
                    Description = table.Column<string>(nullable: true),
                    QuestionnaireId = table.Column<int>(nullable: false),
                    Discriminator = table.Column<string>(nullable: false),
                    PossibleAnswers = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Questions", x => x.QuestionId);
                    table.ForeignKey(
                        name: "FK_Questions_Questionnaires_QuestionnaireId",
                        column: x => x.QuestionnaireId,
                        principalTable: "Questionnaires",
                        principalColumn: "QuestionnaireId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Answers",
                columns: table => new
                {
                    QuestionId = table.Column<int>(nullable: false),
                    UserId = table.Column<int>(nullable: false),
                    Observations = table.Column<string>(nullable: true),
                    Discriminator = table.Column<string>(nullable: false),
                    AnswerChoiceId = table.Column<int>(nullable: true),
                    Answer = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Answers", x => new { x.QuestionId, x.UserId });
                    table.ForeignKey(
                        name: "FK_Answers_Questions_QuestionId",
                        column: x => x.QuestionId,
                        principalTable: "Questions",
                        principalColumn: "QuestionId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Answers_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "UserId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Answers_AnswerChoices_AnswerChoiceId",
                        column: x => x.AnswerChoiceId,
                        principalTable: "AnswerChoices",
                        principalColumn: "AnswerChoiceId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "QuestionAnswerChoices",
                columns: table => new
                {
                    QuestionId = table.Column<int>(nullable: false),
                    AnswerChoiceId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QuestionAnswerChoices", x => new { x.QuestionId, x.AnswerChoiceId });
                    table.ForeignKey(
                        name: "FK_QuestionAnswerChoices_AnswerChoices_AnswerChoiceId",
                        column: x => x.AnswerChoiceId,
                        principalTable: "AnswerChoices",
                        principalColumn: "AnswerChoiceId",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_QuestionAnswerChoices_Questions_QuestionId",
                        column: x => x.QuestionId,
                        principalTable: "Questions",
                        principalColumn: "QuestionId",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.InsertData(
                table: "AnswerChoices",
                columns: new[] { "AnswerChoiceId", "Name" },
                values: new object[,]
                {
                    { 1, "A" },
                    { 2, "B" },
                    { 3, "C" },
                    { 4, "D" }
                });

            migrationBuilder.InsertData(
                table: "Inquiries",
                columns: new[] { "InquiryId", "Name" },
                values: new object[,]
                {
                    { 1, "Inquérito Pós-Parto" },
                    { 2, "Inquérito de Satisfação" }
                });

            migrationBuilder.InsertData(
                table: "Users",
                columns: new[] { "UserId", "IsAdmin", "Name", "Password", "PersonalId" },
                values: new object[,]
                {
                    { 1, true, "José Simões", "$2b$12$boHLVtvH8J5lW//wh8no8.IKugql6MkBSiBDhE2ZXSgOOpqOg6vsO", 170100228 },
                    { 2, false, "Ricardo Silva", "$2b$12$KjnyU9JPmABFOPtWZe4MEe9PoBZwz19WW4NBxUdgc.HQN8e3kxJAa", 170100229 }
                });

            migrationBuilder.InsertData(
                table: "Questionnaires",
                columns: new[] { "QuestionnaireId", "InquiryId", "Name" },
                values: new object[,]
                {
                    { 1, 1, "Avaliação dos funcionários" },
                    { 2, 1, "Avaliação de aspetos secundários do hospital" }
                });

            migrationBuilder.InsertData(
                table: "UserInquiries",
                columns: new[] { "UserId", "InquiryId" },
                values: new object[,]
                {
                    { 2, 1 },
                    { 2, 2 }
                });

            migrationBuilder.InsertData(
                table: "Questions",
                columns: new[] { "QuestionId", "Description", "Discriminator", "Name", "QuestionnaireId", "PossibleAnswers" },
                values: new object[] { 2, "Avalie a simpatia dos funcionários.", "ChoiceQuestion", "Avaliação da simpatia", 1, 1 });

            migrationBuilder.InsertData(
                table: "Questions",
                columns: new[] { "QuestionId", "Description", "Discriminator", "Name", "QuestionnaireId" },
                values: new object[] { 1, "Descreva o seu veredicto final dos funcionários neste hospital.", "Question", "Avaliação geral dos funcionários", 1 });

            migrationBuilder.InsertData(
                table: "Questions",
                columns: new[] { "QuestionId", "Description", "Discriminator", "Name", "QuestionnaireId", "PossibleAnswers" },
                values: new object[] { 3, "Avalie a qualidade do estacionamento.", "ChoiceQuestion", "Avaliação do estacionamento", 2, 1 });

            migrationBuilder.InsertData(
                table: "QuestionAnswerChoices",
                columns: new[] { "QuestionId", "AnswerChoiceId" },
                values: new object[,]
                {
                    { 2, 1 },
                    { 2, 2 },
                    { 2, 3 },
                    { 2, 4 },
                    { 3, 1 },
                    { 3, 2 },
                    { 3, 3 },
                    { 3, 4 }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Answers_UserId",
                table: "Answers",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_Answers_AnswerChoiceId",
                table: "Answers",
                column: "AnswerChoiceId");

            migrationBuilder.CreateIndex(
                name: "IX_QuestionAnswerChoices_AnswerChoiceId",
                table: "QuestionAnswerChoices",
                column: "AnswerChoiceId");

            migrationBuilder.CreateIndex(
                name: "IX_Questionnaires_InquiryId",
                table: "Questionnaires",
                column: "InquiryId");

            migrationBuilder.CreateIndex(
                name: "IX_Questions_QuestionnaireId",
                table: "Questions",
                column: "QuestionnaireId");

            migrationBuilder.CreateIndex(
                name: "IX_UserInquiries_InquiryId",
                table: "UserInquiries",
                column: "InquiryId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Answers");

            migrationBuilder.DropTable(
                name: "QuestionAnswerChoices");

            migrationBuilder.DropTable(
                name: "UserInquiries");

            migrationBuilder.DropTable(
                name: "AnswerChoices");

            migrationBuilder.DropTable(
                name: "Questions");

            migrationBuilder.DropTable(
                name: "Users");

            migrationBuilder.DropTable(
                name: "Questionnaires");

            migrationBuilder.DropTable(
                name: "Inquiries");
        }
    }
}
