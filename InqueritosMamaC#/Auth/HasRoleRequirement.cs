using System;
using Microsoft.AspNetCore.Authorization;

namespace QuestionarioC_.Auth
{
    public class HasRoleRequirement : IAuthorizationRequirement
    {
        public string Role { get; }

        public HasRoleRequirement(string role)
        {
            Role = role ?? throw new ArgumentNullException(nameof(role));
        }
    }
}