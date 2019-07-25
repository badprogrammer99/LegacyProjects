<?php

namespace App\Http\Middleware;

use Closure;

class CheckIfAuthorizationHeaderIsPresent
{
    /**
     * Handle an incoming request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Closure  $next
     * @return mixed
     */
    public function handle($request, Closure $next)
    {
        if ($request->headers->has('Authorization')) {
            return response()->json(['error' => 'Trying to access web resources with an Authorization header (most likely with an attached Bearer token). Remove it and try again.'], 401);
        }
        return $next($request);
    }
}
