#!/usr/bin/ruby -w
#Handles the File I/O from the CSV file, and writes a new one.
require 'csv'
require './Poro.rb'
#my name to be shown in screenshots
$name = "Dan Squirrell - s040634250"
# This class handles direct access to the CSV. It allows us to read or right to CSV
class ReadCsv
  #this function reads in the CSV and converts it into an ascosiative array
  def readIn
    csv_content = File.read(".//InternationalCovid19Cases.csv") #reading in the csv file from file path
    $csv = CSV.parse(csv_content, :headers => true) #parsing the csv content into rows
  rescue
    puts "#{$name}"#generates a file not found message if the file does not exist (begin,rescue,end similar to try catch block in java)
    puts "File Not Found"
  end
  #this function takes the array and writes each entry (object) to a new CSV file
  def writeOut
    CSV.open(".//InternationalCovid19CasesEdited.csv", "w") do |csv| #open seperate CSV to write to
      @i = 0
      while @i < $new_array.length
        @written_countryCode = $new_array[@i][0]
        @written_date = $new_array[@i][1]
        @written_cases = $new_array[@i][2]
        @written_deaths = $new_array[@i][3]
        @written_name_en = $new_array[@i][4]
        @written_name_fr = $new_array[@i][5]

        csv << [@written_countryCode,@written_date,@written_cases,@written_deaths,@written_name_en,@written_name_fr]
        @i+=1
      end
    end
  end
end
