using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace QuestionarioC_.Models.Context
{
    public class ApplicationContext : DbContext
    {
        private static readonly LoggerFactory _myLoggerFactory = new LoggerFactory(new[]
        {
            new Microsoft.Extensions.Logging.Debug.DebugLoggerProvider()
        });

        public DbSet<User> Users { get; set; }
        public DbSet<Inquiry> Inquiries { get; set; }
        public DbSet<Questionnaire> Questionnaires { get; set; }
        public DbSet<Question> Questions { get; set; }
        public DbSet<ChoiceQuestion> ChoiceQuestions { get; set; }
        public DbSet<AnswerChoice> AnswerChoices { get; set; }
        public DbSet<FreeAnswer> FreeAnswers { get; set; }
        public DbSet<ChosenAnswer> ChosenAnswers { get; set; }

        private DbSet<UserInquiry> UserInquiries { get; set; }
        private DbSet<QuestionAnswerChoice> QuestionAnswerChoices { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySql("Host=127.0.0.1;Database=questionario;Username=root;Password=");
            optionsBuilder.UseLoggerFactory(_myLoggerFactory);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<UserInquiry>().HasKey(l => new {l.UserId, l.InquiryId});
            modelBuilder.Entity<QuestionAnswerChoice>().HasKey(l => new {l.QuestionId, l.AnswerChoiceId});
            modelBuilder.Entity<Answer>().HasKey(l => new {l.QuestionId, l.UserId});
            modelBuilder.Entity<Answer>().ToTable("Answers");
            modelBuilder.Seed();
        }
    }
}