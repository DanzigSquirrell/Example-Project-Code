#!/usr/bin/ruby -w
#displays the standard record view or graph depending on method called
class RecordDisplay
  #Prints the UI for the record or records selected
  #Params:
  #+oneOrAll+::One record or all records, boolean
  #+printID+::If only one record is to be printed, this is its ID
  def displayRecord(oneOrAll,printID)
    $businessMethods = RecordArray.new
    if oneOrAll == "1" #If one record
      @rec_num = printID.to_i
      printedRecord = $businessMethods.getRecord(@rec_num - 1)

      puts "___________________________________________________"
      puts "Record Number: #{@rec_num}"
      puts "___________________________________________________"
      puts "Country Code: #{printedRecord.getCountryCode}"
      puts "Date: #{printedRecord.getDate}"
      puts "Cases: #{printedRecord.getCases}"
      puts "Deaths: #{printedRecord.getDeaths}"
      puts "French Name of Country: #{printedRecord.getName_fr}"
      puts "English Name of Country: #{printedRecord.getName_en}"
      puts "___________________________________________________"
    else
      @i = 0
      while @i < $businessMethods.getArray.length #loops through all records to print them
        printedRecord = $businessMethods.getRecord(@i)
        puts "___________________________________________________"
        puts "Record Number: #{@i+1}"
        puts "___________________________________________________"
        puts "Country Code: #{printedRecord.getCountryCode}"
        puts "Date: #{printedRecord.getDate}"
        puts "Cases: #{printedRecord.getCases}"
        puts "Deaths: #{printedRecord.getDeaths}"
        puts "French Name of Country: #{printedRecord.getName_fr}"
        puts "English Name of Country: #{printedRecord.getName_en}"
        @i+=1
      end
      puts "___________________________________________________"
    end
    return true #for testing purposes. Will return null if this function does not run.
  end
  #displays graphs depending on user choise
  def displayGraph(choice)
    if choice == "1"  #displays cases graph
        @i=0
        @j=$case_count/50
        puts ""
        puts "each ■ represents 50 cases"
        puts ""
        while @i < $case_count / 50
          if (@j * 50) >= 100
            puts "#{@j * 50}    ■ "
          else
            puts "#{@j * 50}     ■ "
          end
          @i+=1
          @j-=1
        end
        puts "     Cases"
    end
    if choice == "2" #displays death graph
      @k=0
      @l=$death_count/5
      puts ""
      puts "each @ represents 5 deaths"
      puts ""
      while @k < $death_count / 5
        if(@l * 5) >= 10
          puts "#{@l * 5}     @ "
        else puts "#{@l * 5}      @ "
        end
        @k+=1
        @l-=1
      end
      puts "     Deaths"
    end
    if choice == "3"  #displays both graphs
      @i=0
      @j=$case_count/50
      @k=0
      @l=$death_count/5
      puts ""
      puts "each ■ represents 50 cases"
      puts "each @ represents 5 deaths"
      puts ""
      while @i < $case_count / 50 and @j > @l
        if (@j * 50) >= 100
          puts "#{@j * 50}    ■ "
        else
          puts "#{@j * 50}     ■ "
        end
        @i+=1
        @j-=1
      end
      while @i < $case_count / 50
        if (@j * 50) >= 100
          puts "#{@j * 50}    ■           @      #{@l * 5}"
        else
          puts "#{@j * 50}     ■           @      #{@l * 5}"
        end
        @i+=1
        @j-=1
        @l-=1
      end

      puts "     Cases      Deaths"
    end
  end
end
