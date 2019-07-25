using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;

namespace QuestionarioC_.Auth
{
    public class HasRoleHandler : AuthorizationHandler<HasRoleRequirement>
    {
        protected override Task HandleRequirementAsync(AuthorizationHandlerContext context,
            HasRoleRequirement requirement)
        {
            const string roleName = "http://schemas.microsoft.com/ws/2008/06/identity/claims/role";

            // if invalid role, deny access
            if (!context.User.HasClaim(c => c.Type == roleName))
                return Task.CompletedTask;

            var role = context.User.FindFirst(c => c.Type == roleName).Value;

            // Succeed if the role array contains the required role
            if (role == requirement.Role)
                context.Succeed(requirement);

            return Task.CompletedTask;
        }
    }
}