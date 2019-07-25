using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations.Schema;

namespace QuestionarioC_.Models
{
    public class UserInquiry
    {
        [JsonIgnore]
        public int UserId { get; set; }
        [ForeignKey("UserId")]
        public User User { get; set; }
        
        public int InquiryId { get; set; }
        [ForeignKey("InquiryId")]
        public Inquiry Inquiry { get; set; }
    }
}