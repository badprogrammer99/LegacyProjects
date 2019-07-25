using QuestionarioC_.Helpers;
using Microsoft.EntityFrameworkCore;

namespace QuestionarioC_.Models.Context
{
    public static class ModelBuilderExtensions
    {
        public static void Seed(this ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<User>().HasData(
                new User
                {
                    UserId = 1,
                    PersonalId = 170100228,
                    Name = "José Simões", 
                    Password = BCryptHelper.HashPassword("933957139"), 
                    IsAdmin = true
                },
                new User
                {
                    UserId = 2,
                    PersonalId = 170100229, 
                    Name = "Ricardo Silva", 
                    Password = BCryptHelper.HashPassword("123456"), 
                    IsAdmin = false
                }
            );

            modelBuilder.Entity<Inquiry>().HasData(
                new Inquiry
                {
                    InquiryId = 1,
                    Name = "Inquérito Pós-Parto"
                },
                new Inquiry 
                {
                    InquiryId = 2,
                    Name = "Inquérito de Satisfação"
                }
            );

            modelBuilder.Entity<UserInquiry>().HasData(
                new UserInquiry
                {
                    UserId = 2,
                    InquiryId = 1
                },
                new UserInquiry
                {
                    UserId = 2,
                    InquiryId = 2
                }
            );

            modelBuilder.Entity<Questionnaire>().HasData(
                new Questionnaire
                {
                    QuestionnaireId = 1,
                    InquiryId = 1,
                    Name = "Avaliação dos funcionários"
                },
                new Questionnaire
                {
                    QuestionnaireId = 2,
                    InquiryId = 1,
                    Name = "Avaliação de aspetos secundários do hospital"
                }
            );

            modelBuilder.Entity<AnswerChoice>().HasData(
                new AnswerChoice
                {
                    AnswerChoiceId = 1,
                    Name = "A"
                },
                new AnswerChoice
                {
                    AnswerChoiceId = 2,
                    Name = "B"
                },
                new AnswerChoice
                {
                    AnswerChoiceId = 3,
                    Name = "C"
                },
                new AnswerChoice
                {
                    AnswerChoiceId = 4,
                    Name = "D"
                }
            );

            modelBuilder.Entity<Question>().HasData(
                new Question
                {
                    QuestionId = 1, QuestionnaireId = 1, Name = "Avaliação geral dos funcionários",
                    Description = "Descreva o seu veredicto final dos funcionários neste hospital."
                }
            );

            modelBuilder.Entity<ChoiceQuestion>().HasData(
                new ChoiceQuestion
                {
                    QuestionId = 2, QuestionnaireId = 1, Name = "Avaliação da simpatia",
                    Description = "Avalie a simpatia dos funcionários.", PossibleAnswers = 1
                },
                new ChoiceQuestion
                {
                    QuestionId = 3, QuestionnaireId = 2, Name = "Avaliação do estacionamento",
                    Description = "Avalie a qualidade do estacionamento.", PossibleAnswers = 1
                }
            );

            modelBuilder.Entity<QuestionAnswerChoice>().HasData(
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 1,
                    QuestionId = 2
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 2,
                    QuestionId = 2
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 3,
                    QuestionId = 2
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 4,
                    QuestionId = 2
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 1,
                    QuestionId = 3
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 2,
                    QuestionId = 3
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 3,
                    QuestionId = 3
                },
                new QuestionAnswerChoice
                {
                    AnswerChoiceId = 4,
                    QuestionId = 3
                }
            );
        }
    }
}