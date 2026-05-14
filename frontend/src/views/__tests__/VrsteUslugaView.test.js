import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import VrsteUslugaView from '../VrsteUslugaView.vue'

vi.mock('@/services/api', () => {
  return {
    default: {
      get: vi.fn(() =>
        Promise.resolve({
          data: [
            {
              vrstaId: 1,
              naziv: 'Sport',
              opis: 'Sportske aktivnosti'
            },
            {
              vrstaId: 2,
              naziv: 'Wellness',
              opis: 'Wellness usluge'
            }
          ]
        })
      ),
      post: vi.fn(() => Promise.resolve()),
      put: vi.fn(() => Promise.resolve()),
      delete: vi.fn(() => Promise.resolve())
    }
  }
})

describe('VrsteUslugaView', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('prikazuje naslov stranice', () => {
    const wrapper = mount(VrsteUslugaView)

    expect(wrapper.text()).toContain('Vrste usluga')
  })

  it('prikazuje vrste usluga dohvaćene s API-ja', async () => {
    const wrapper = mount(VrsteUslugaView)

    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.text()).toContain('Sport')
    expect(wrapper.text()).toContain('Wellness')
  })

  it('filtrira vrste usluga prema unosu u pretragu', async () => {
    const wrapper = mount(VrsteUslugaView)

    await new Promise(resolve => setTimeout(resolve, 0))

    const input = wrapper.find('.search-input')
    await input.setValue('sport')

    expect(wrapper.text()).toContain('Sport')
    expect(wrapper.text()).not.toContain('Wellness')
  })
})