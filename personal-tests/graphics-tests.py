import os, sys
import pygame
from pygame.locals import *

if not pygame.font: print 'fonts disabled'
if not pygame.mixer: print 'sound disabled'

pygame.init()

screen = pygame.display.set_mode((400, 200))
background = pygame.Surface(screen.get_size())
background = background.convert()
background.fill((250,250,250))

if pygame.font:
    font = pygame.font.Font(None, 36)
    text = font.render("Test Graphics", 1, (10, 10, 10))
    textpos = text.get_rect(centerx=background.get_width()/2)
    background.blit(text, textpos)

screen.blit(background, (0,0))
pygame.display.flip()
pygame.draw.circle(background, (0,0,0), (0,0), 50, 5)

while 1:
    for event in pygame.event.get():
        if event.type == QUIT:
            return
