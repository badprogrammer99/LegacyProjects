<?php

namespace App\Http\Controllers;

use Auth;
use PDF;
use App\Inquiry;
use App\Http\Resources\InquiryCollection;
use App\Http\Resources\InquiryResource;
use Illuminate\Http\Request;

class InquiryController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @param \App\Inquiry $inquiry
     * @return \App\Http\Resources\InquiryCollection
     */
    public function index(Inquiry $inquiry)
    {
        return new InquiryCollection($inquiry->orderBy('name')->get());
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $inquiry = new Inquiry();

        $inquiry->name = $request->input('inquiry_name');

        if ($inquiry->save()) {
            return response()->json($inquiry, 200);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
		$inquiry = Inquiry::with('questionnaries')->findOrFail($id);
		
		return new InquiryResource($inquiry);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $inquiry = Inquiry::findOrFail($id);

        $inquiry->name = $request->input('inquiry_name');

        if ($inquiry->save()) {
            return response()->json($inquiry, 200);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $inquiry = Inquiry::findOrFail($id);
		
        if ($inquiry->delete()) {
			return response()->json(['msg' => 'Record deleted succesfully'], 200);
		}
    }

    /**
     * Generate a information PDF file about an inquiry.
     * @param  int  $id
     * @return void
     */
    public function generateInquiryPdf($id) 
    {
        $inquiry = Inquiry::with('questionnaries.questions.PossibleAnswerNames')->findOrFail($id);

        $user = Auth::user()->patient_name;

        $pdf = PDF::loadView('pdf.pdf_template', compact('inquiry', 'user'));
        
        return $pdf->download('inquerito.pdf');
    }
}
