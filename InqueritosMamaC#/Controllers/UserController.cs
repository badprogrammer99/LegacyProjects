using System;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Filters;
using QuestionarioC_.Interfaces.Repositories;

namespace QuestionarioC_.Controllers
{
    [Authorize("User")]
    [Route("api/[controller]")]
    public class UserController : Controller
    {
        private readonly IUserRepository _userRepository;
        private int _userId;

        public UserController(IUserRepository userRepository)
        {
            _userRepository = userRepository;
        }

        public override void OnActionExecuting(ActionExecutingContext context)
        {
            _userId = Convert.ToInt32(User.FindFirst(ClaimTypes.Name)?.Value);
        }

        [HttpPost("[action]")]
        public async Task<IActionResult> SetUserPassword(string oldPassword, string newPassword)
        {
            IActionResult actionResult = null;

            var setUserPasswordResult = await _userRepository.SetUserPassword(_userId, oldPassword, newPassword);

            if (setUserPasswordResult)
            {
                actionResult = Json(new {status = "The password has been set successfully."});
            }

            return actionResult;
        }

        [HttpGet("[action]")]
        public async Task<IActionResult> FetchUserInquiries()
        {
            var user = _userRepository.GetUserByPersonalId(_userId);

            if (user.IsAdmin)
            {
                return Json(new {message = "Administrators don't have attached inquiries!"});
            }

            var userInquiries = await _userRepository.FetchUserInquiries(user.UserId);

            return Json(userInquiries);
        }
    }
}