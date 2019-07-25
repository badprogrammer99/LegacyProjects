using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models
{
    public class Question
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int QuestionId { get; set; }

        public string Discriminator { get; private set; }
        public string Name { get; set; }
        public string Description { get; set; }

        public int QuestionnaireId { get; set; }
        [ForeignKey("QuestionnaireId")] public Questionnaire Questionnaire { get; set; }
    }
}