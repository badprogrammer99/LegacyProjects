using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Newtonsoft.Json;

namespace QuestionarioC_.Models
{
    public class User
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        [JsonIgnore]
        public int UserId { get; set; }
        public int PersonalId { get; set; }
        public string Name { get; set; }
        [JsonIgnore] public string Password { get; set; }
        public bool IsAdmin { get; set; }
        public ICollection<UserInquiry> UserInquiries { get; set; }
    }
}