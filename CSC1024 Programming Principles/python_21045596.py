import random

def create_board():
    board = []
    for i in range(3):
        row = []
        for j in range(3):
            row.append('-')
        board.append(row)
    return board


def show_board():
    for row in board:
        for item in row:
            print(item, end = " ")
        print()


def choose_first_player():    
    player_list = ['Computer', 'Player']
    i = random.randint(0,1)
    player = player_list[i]
    return player


def assign_mark():
    mark_list = ['X', 'O']
    i = random.randint(0,1)
    c_mark = mark_list[i]

    #check if computer's and player's piece is same
    h_mark = 'X'
    if h_mark == c_mark:
        h_mark = 'O'

    return c_mark, h_mark
                

def is_board_filled():
    for row in board:
        for item in row:
            if item == '-':
                return False
    return True


def swap_turn(player):
    if player == 'Computer':
        player = 'Player'
    else:
        player = 'Computer'
    return player


def computer_move(player, text, mark, turn):
    row = random.randint(0,2)
    col = random.randint(0,2)

    #prevent repetitive text when computer fix the piece position again
    if text == 1:
        print("Waiting for computer...")

    mark_position(row, col, mark, player, turn)

    
def human_move(player, mark, turn):
    print("Player's turn, you are \"", mark, "\"")
    error = True

    #handle input exceed row/col available error
    while error:
        row = int(input("Enter row number to place your piece: "))
        col = int(input("Enter col number to place your piece: "))
        if row > 3 or col > 3:
            print("《Exceed available row or column number.》")
        else:
            error = False
        
    mark_position(row-1, col-1, mark, player, turn)


def mark_position(row, col, mark, player, turn):
    #check if position is filled, if yes prompt current player to fix a position again
    if board[row][col] == '-':
        log_file(player, row, col, mark, turn)
        board[row][col] = mark
    elif player == 'Player':
        print("《Error, position filled.》")
        human_move('Player', mark, turn)
    else:
        computer_move('Computer', 0, mark, turn)

        
def log_file(player, row, col, mark, turn):
    if player == "Computer":
        player = 'C'
    else:
        player = 'H'
    f.write(str(turn) + ", " + str(player) + ", " + str(row+1) + ", " + str(col+1) + ", " + str(mark) + "\n")
  
  
def is_player_win(player):
    win = None
    n = len(board)

    if player == 'Computer':
        mark = c_mark
    else:
        mark = h_mark

    #checking rows
    for i in range(n):
        win = True
        for j in range(n):
            if board[i][j] != mark:
                win = False
                break
        if win:
            return win

    #checking columns
    for i in range(n):
        win = True
        for j in range(n):
            if board[j][i] != mark:
                win = False
                break
        if win:
            return win

    #checking diagonals
    #Leading diagonal
    win = True
    for i in range(n):
        if board[i][i] != mark:
            win = False
            break
    if win:
        return win
    
    #Anti-diagonal
    win = True
    for i in range(n):
        if board[i][n - 1 - i] != mark:
            win = False
            break
    if win:
        return win
    return False


def start(board, player, c_mark, h_mark):
    turn = 1
    ongoing = True
    
    while ongoing:
        if player == 'Computer':
            computer_move('Computer', 1, c_mark, turn)
        else:
            human_move('Player', h_mark, turn)
    
        show_board()
        
        #checking if win or tie condition is met
        if is_player_win(player):
            print(player, "win!")
            ongoing = False
        elif is_board_filled():
            print("Game Over. It's a tie.")
            ongoing = False
            
        turn += 1
        player = swap_turn(player)


def play_again():
    invalid = True
    while invalid:
        retry = input("Do you wish to play again? [Y/N]:")

        if retry.upper() == 'Y':
            return True
        elif retry.upper() == 'N':
            print("Game end.")
            return False
        else:
            print("《Incorrect input, please try again.》")
        


round_count = 1    
again = True
f = open("logfile_21045596.txt", "w")

while again:
    f = open("logfile_21045596.txt", "a")
    f.write("\nRound " + str(round_count)+ "\n")
    
    board = create_board()
    show_board()

    player = choose_first_player()
    c_mark, h_mark = assign_mark()
    #c-mark = computer's piece, h_mark, user's piece

    print("Tic-Tac-Toe Game Start")
    start(board, player, c_mark, h_mark)
    
    again = play_again()
    round_count += 1


f.close()
        


    







