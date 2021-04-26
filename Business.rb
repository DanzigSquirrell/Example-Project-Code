#!/usr/bin/ruby -w
#Handles the creation of the array containing record objects from persistence.
require './Persistence.rb'
# This class communicates with the persistence portion of the project. It alters
# the CSV file into an associative array and allows us to manipulate the contents.
class RecordArray
  @@persist = ReadCsv.new
  @@persist.readIn
  $new_array = Array.new(101) {Array.new(6)}
  #Takes the information from the CSV to create record objects and add them to the array.
  #Ruby was giving me problems with objects in an array, so I used a hashmap style data structure
  #to hold the information
  #
  #returns:: the array full of the new information from the CSV
  def generate_array
    i=0
    while i < 101
        @parsed_countryCode = $csv[i][0]
        @parsed_date = $csv[i][1]
        @parsed_cases = $csv[i][2]
        @parsed_deaths = $csv[i][3]
        @parsed_name_en = $csv[i][4]
        @parsed_name_fr = $csv[i][5]

        @record_to_add = Record.new(@parsed_countryCode,@parsed_date,@parsed_cases,@parsed_deaths,@parsed_name_en,@parsed_name_fr) #creates a record object for the purposes of this assignment.

        $new_array[i][0] = @record_to_add.getCountryCode
        $new_array[i][1] = @record_to_add.getDate
        $new_array[i][2] = @record_to_add.getCases
        $new_array[i][3] = @record_to_add.getDeaths
        $new_array[i][4] = @record_to_add.getName_en
        $new_array[i][5] = @record_to_add.getName_fr

        i+=1
    end
    return $new_array
  end
  #takes the record it is passed and adds it to the end of the Array
  #params:
  #+record+::record to be added
  def append_to_array(record)
      appendArray = deconstruct record
      $new_array.push(appendArray)
  end
  #returns the array to be manipulated from other classes
  #returns:: the array
  def getArray
    return $new_array
  end
  #returns a record object with the passed ID
  #params:
  #+recordID+:: Id of the record to be returned
  #returns:: record created from array
  def getRecord(recordID)
    id = recordID
    record = Record.new($new_array[id][0],$new_array[id][1],$new_array[id][2],$new_array[id][3],$new_array[id][4],$new_array[id][5])
    return record
  end
  #completely refreshes the array from csv file =, erasing any changes
  #returns:: The newly refreshed array
  def reloadArray
    $new_array = Array.new(101) {Array.new(6)}
    generate_array
    return $new_array
  end
  #Edits the array entry based on the record passed to it
  #params:
  #+record_number+:: the record number to be edited
  #+record+:: the new record that takes the place of the old
  def editToArray(record_number,record)
    editArray = deconstruct record
    $new_array[record_number.to_i] = editArray
  end
  #Deletes the array entry at target INDEX
  #params:
  #+record_number+:: record to be deleted
  def deleteFromArray(record_number)
    $new_array.delete_at(record_number.to_i-1)
  end
  #As stated above, ruby was not letting me hold an array of objects properly.
  #this function takes the record and turns it into an indexed array.
  #+record+::record to be converted to an array
  #returns::the array representing an object
  def deconstruct(record)
    recordCode = record.getCountryCode
    recordDate = record.getDate
    recordCases = record.getCases
    recordDeaths = record.getDeaths
    recordEnName = record.getName_en
    recordFrName = record.getName_en

    deconstructedRecord = Array.new(6)
    deconstructedRecord[0]=recordCode
    deconstructedRecord[1]=recordDate
    deconstructedRecord[2]=recordCases
    deconstructedRecord[3]=recordDeaths
    deconstructedRecord[4]=recordEnName
    deconstructedRecord[5]=recordFrName

    return deconstructedRecord
  end
  #calls on the method which writes the array to CSV. this method only exists here so that
  #the presentation file does not need to interact with persistence
  def writeArray
    @@persist.writeOut
  end
  #counts the number of cases and deaths in the first 100 records
  def getGraphCount
    @i = 0
    $case_count = 0
    $death_count = 0
    while @i < 100

      $case_count = $new_array[@i][2].to_i + $case_count
      #puts $case_count

      $death_count = $new_array[@i][3].to_i+ $death_count
      #puts $death_count
      @i+=1
    end
  end
end
