#!/usr/bin/ruby -w
#starts the program.
require '.\Presentation.rb'
require '.\Persistence.rb'
# This class serves as a starting point for the program.
class Jump
present = UserPresentation.new
present.menuOptions true

end
